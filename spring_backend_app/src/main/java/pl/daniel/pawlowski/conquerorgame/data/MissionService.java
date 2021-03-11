package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.UnitService;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.MissionRepository;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
@Slf4j
public class MissionService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnitService unitService;

    @Autowired
    DungeonsService dungeonsService;

    @Autowired
    BattleService battleService;

    @Autowired
    HeroService heroService;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    ObjectMapper mapper;

    public String missionAction(UserAction action) throws JsonProcessingException {
        MissionJSON missionJson = mapper.readValue(action.getData(), MissionJSON.class);
        Mission mission = mapMissionJsonToDTO(missionJson, action.getUser().getCityPosition());
        mission.getHero().setUser(action.getUser());
        mission.getHero().getItems().forEach(item -> item.setHero(mission.getHero()));
        action.getUser().addMission(mission);
        subtractUnits(action.getUser().getUnits(), mission.getUnits());
        return userService.saveUser(action.getUser()) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    private void subtractUnits(Units userUnits, Units unitsMission) {
        userUnits.setUnit1(userUnits.getUnit1() - unitsMission.getUnit1());
        userUnits.setUnit2(userUnits.getUnit2() - unitsMission.getUnit2());
        userUnits.setUnit3(userUnits.getUnit3() - unitsMission.getUnit3());
        userUnits.setUnit4(userUnits.getUnit4() - unitsMission.getUnit4());
        userUnits.setUnit5(userUnits.getUnit5() - unitsMission.getUnit5());
        userUnits.setUnit6(userUnits.getUnit6() - unitsMission.getUnit6());
    }

    public Mission mapMissionJsonToDTO(MissionJSON missionJSON, int playerPosition) {
        Mission mission = new Mission();

        mission.setHero(missionJSON.getHero());
        mission.setType(missionJSON.getType());
        mission.setUnits(missionJSON.getUnits());
        mission.setMissionArrivalTime(calculateTime(playerPosition, missionJSON.getTarget(), null));
        mission.setMissionFinishTime(mission.getMissionArrivalTime().plusHours(missionJSON.getTime()));
        mission.setMissionReturnTime(calculateTime(playerPosition, missionJSON.getTarget(), mission.getMissionFinishTime()));
        mission.setTarget(missionJSON.getTarget());

        return mission;
    }

    public void calculateMission(Mission mission, User user) {
        switch (mission.getCalculations()) {
            case 0:
                performArrivalAction(mission);
                break;
            case 1:
                performFinishAction(mission);
                break;
            case 2:
                performReturnAction(mission, user);
                break;
        }
    }

    private void performArrivalAction(Mission mission) {
        if (MISSION_ATTACK.equals(mission.getType()))
            performFinishAction(mission);
        else
            mission.setCalculations(1);
    }

    private void performFinishAction(Mission mission) {
        if (MISSION_ATTACK.equals(mission.getType())) {
            List<Unit> unitsAttacking = getBattleUnits(mission.getUser().getFraction(), mission.getUnits(), mission.getHero());
            List<List<Unit>> unitsDefending = mission.getTarget() == 0 ? Collections.singletonList(getDungeonLevelUnits(mission.getHero())) : getDefendingUnitsFromCity(mission.getTarget());
            battleService.battle(unitsAttacking, unitsDefending);
        }
        mission.setCalculations(2);
    }


    private void performReturnAction(Mission mission, User user) {
        Units userUnits = mission.getUser().getUnits();

        log.info("Units before: " + userUnits);

        userUnits.setUnit1(userUnits.getUnit1() + mission.getUnits().getUnit1());
        userUnits.setUnit2(userUnits.getUnit2() + mission.getUnits().getUnit2());
        userUnits.setUnit3(userUnits.getUnit3() + mission.getUnits().getUnit3());
        userUnits.setUnit4(userUnits.getUnit4() + mission.getUnits().getUnit4());
        userUnits.setUnit5(userUnits.getUnit5() + mission.getUnits().getUnit5());
        userUnits.setUnit6(userUnits.getUnit6() + mission.getUnits().getUnit6());

        log.info("Units set : " + userUnits);
        log.info("Resources before :  Gold : " + mission.getUser().getGold() + " Wood: " + mission.getUser().getWood() + " Stone: " + mission.getUser().getStone());

        mission.getUser().setGold(mission.getUser().getGold() + mission.getGold());
        mission.getUser().setWood(mission.getUser().getWood() + mission.getWood());
        mission.getUser().setStone(mission.getUser().getStone() + mission.getStone());

        log.info("Resources after :  Gold : " + mission.getUser().getGold() + " Wood: " + mission.getUser().getWood() + " Stone: " + mission.getUser().getStone());
        mission.setCalculations(3);
        userService.saveUser(user);
    }

    public void deleteFinishedMissions() {
        List<Mission> finishedMissions = missionRepository.findByCalculations(3);
        for (Mission m : finishedMissions) {
            User user = m.getUser();
            user.removeMission(m);
            userService.saveUser(user);
        }
    }

    private LocalDateTime calculateTime(int startPosition, int finishPosition, LocalDateTime startDateTime) {
        LocalDateTime calcStartDateTime;
        if (startDateTime != null)
            calcStartDateTime = startDateTime;
        else
            calcStartDateTime = LocalDateTime.now();

        if (finishPosition == 0) {
            return calcStartDateTime.plusMinutes(30);
        } else {
            double xStartPosition = Math.ceil(startPosition / 4);
            double yStartPosition = (startPosition - 4 * (xStartPosition - 1));
            double xFinishPosition = Math.ceil(finishPosition / 4);
            double yFinishPosition = (finishPosition - 4 * (xFinishPosition - 1));
            double distance = Point2D.distance(xStartPosition, yStartPosition, xFinishPosition, yFinishPosition);
            return calcStartDateTime.plusMinutes((int) (distance * 30));
        }
    }

    private List<Unit> getDungeonLevelUnits(Hero hero){
        List<Unit> dungeonUnits = dungeonsService.getLevelUnits(hero);
        dungeonUnits.forEach(unit ->
            unit.copyUnitValues(unitService.getDungeonUnitByName(unit.getName()))
        );
        return dungeonUnits;
    }


    public List<List<Unit>> getDefendingUnitsFromCity(int target){
        List<List<Unit>> defendingArmies = new ArrayList<>();

        User defendingUser = userRepository.findOneByCityPosition(target);
        Units unitsCityOwner = defendingUser.getUnits();
        Hero heroDefending = defendingUser.getHeroes().stream().max(Comparator.comparingInt(Hero::getLevel)).get();
        List<Unit> unitsBattleCityOwner = getBattleUnits(defendingUser.getFraction(), unitsCityOwner, heroDefending);
        defendingArmies.add(unitsBattleCityOwner);

        List<Mission> allStationMissions = getAllStationMissionsOfCity(target);

        allStationMissions.forEach(mission -> defendingArmies.add(getBattleUnits(mission.getUser().getFraction(), mission.getUnits(), mission.getHero())));

        return defendingArmies;
    }


    public List<Unit> getBattleUnits(String fraction, Units units, Hero hero) {
        List<Unit> missionBasicUnits = unitService.getFractionUnits(fraction);
        List<Unit> missionBattleUnits = missionBasicUnits.stream()
                .map(Unit::new)
                .collect(Collectors.toList());

        int[] unitsAmount = unitService.getUnitsAmountFromDatabaseUnits(units);


        int unitStatisticsBonus = hero.getStatistics().getCharisma(); // heroService.getHeroMainStatisticValue(mission.getHero());

        missionBattleUnits.forEach(
                unit -> {
                    unit.setAmount(unitsAmount[unit.getLevel() - 1]);
                    unit.setArmour(unit.getArmour() + Math.floor(unitStatisticsBonus / 10) );
                    unit.setDamageMin(unit.getDamageMin() + Math.floor(unitStatisticsBonus / 7));
                    unit.setDamageMax(unit.getDamageMax() + Math.floor(unitStatisticsBonus / 4));
                    unit.setHealthPoints(unit.getHealthPoints() + (int)Math.floor(hero.getStatistics().getStrength() / 10));
                    unit.setArmyId("Army " + units.getId());
                });

        missionBattleUnits.add(heroService.createHeroUnit(hero));

        return missionBattleUnits;
    }


    private List<Mission> getAllStationMissionsOfCity(int city){
        return missionRepository.findByTargetEqualsAndMissionArrivalTimeAfterAndMissionFinishTimeBeforeAndTypeEquals(city, LocalDateTime.now(), LocalDateTime.now(), MISSION_STATION);
    }
}
