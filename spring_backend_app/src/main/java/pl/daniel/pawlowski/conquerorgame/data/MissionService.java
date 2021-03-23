package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.BattleResult;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.UnitService;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.MissionRepository;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.*;
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

    private final int UNIT_RESOURCES_CAPACITY = 400;

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
        else {
            mission.setCalculations(1);
            createMessages(mission, null);
        }
    }

    private void performFinishAction(Mission mission) {
        if (MISSION_ATTACK.equals(mission.getType())) {
            List<Unit> unitsAttacking = getBattleUnits(mission.getUser().getFraction(), mission.getUnits(), mission.getHero());
            List<List<Unit>> unitsDefending = mission.getTarget() == 0 ? Collections.singletonList(getDungeonLevelUnits(mission.getHero())) : getDefendingUnitsFromCity(mission.getTarget());
            BattleResult result = battleService.battle(unitsAttacking, unitsDefending);
            getBattleResult(result, mission);
            log.info("" + mission.getUser());
            userService.saveUser(mission.getUser());
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

        missionBattleUnits.add(heroService.createHeroUnit(hero, "Army " + units.getId()));

        return missionBattleUnits;
    }

    private void getBattleResult(BattleResult result, Mission mission){
        String fractionAttacking = unitService.getUnitFraction(result.getAttackingArmyLoss().keySet().iterator().next());
        mission.setUnits(updateMissionUnits(result.getAttackingArmyEnd(), mission.getUnits(), fractionAttacking));
        Hero heroAttacking = heroService.getHeroByHeroId(mission.getHero().getId());
        double attackingMultiplier;
        double defendingMultiplier;
        if (mission.getTarget() != 0) {
            Hero heroDefending = userRepository.findOneByCityPosition(mission.getTarget()).getHeroes().stream().max(Comparator.comparingInt(Hero::getLevel)).get();
            result.getDefendingArmyEnd().keySet().forEach(army ->
                    updateMissionUnits(result.getDefendingArmyEnd().get(army),
                            unitService.findUnitsByUnitId(Long.valueOf(army.replace("Army ", ""))),
                            unitService.getUnitFraction(result.getDefendingArmyEnd().get(army).keySet().iterator().next()))
            );
            if(ATTACKING_ARMY_INDICATOR.equals(result.getWinner())) {
                getResources(mission);
                attackingMultiplier = 1.0;
                defendingMultiplier = 0.5;
            }
            else {
                attackingMultiplier = 0.5;
                defendingMultiplier = 1.0;
            }
            result.getDefendingArmyLoss().values().forEach(army -> heroService.updateHeroExp(heroAttacking, army, unitService.getUnitFraction(army.keySet().iterator().next()), attackingMultiplier));
            heroService.updateHeroExp(heroDefending, result.getAttackingArmyLoss(), fractionAttacking, defendingMultiplier);
        }
        if(ATTACKING_ARMY_INDICATOR.equals(result.getWinner()) && mission.getTarget() == 0) {
            Dungeon dungeon = heroAttacking.getDungeons().get(heroAttacking.getDungeons().size() - 1);
            heroAttacking.addItem(dungeon.getReward());
            dungeon.setCompleted(1);
            heroAttacking.addDungeon(dungeonsService.createDungeonLevel(dungeon.getLevel() + 1));
            heroService.updateHeroExp(heroAttacking, result.getDefendingArmyLoss().values().iterator().next(), "Dungeons", 1.0);
        }
    }


    private Units updateMissionUnits(HashMap<String, Integer> unitsBattle, Units units, String fraction){
        units.setUnit1(unitService.getUnitCountByLevel(unitsBattle, 1, fraction));
        units.setUnit2(unitService.getUnitCountByLevel(unitsBattle, 2, fraction));
        units.setUnit3(unitService.getUnitCountByLevel(unitsBattle, 3, fraction));
        units.setUnit4(unitService.getUnitCountByLevel(unitsBattle, 4, fraction));
        units.setUnit5(unitService.getUnitCountByLevel(unitsBattle, 5, fraction));
        units.setUnit6(unitService.getUnitCountByLevel(unitsBattle, 6, fraction));
        return units;
    }

    private void getResources(Mission mission)  {
        int resourcesCapacity = mission.getUnits().getUnit1() +  mission.getUnits().getUnit2() + mission.getUnits().getUnit3() + mission.getUnits().getUnit4() + mission.getUnits().getUnit5() + mission.getUnits().getUnit6();
        resourcesCapacity *= UNIT_RESOURCES_CAPACITY;
        User user = userRepository.findOneByCityPosition(mission.getTarget());
        int cityResources = user.getGold() + user.getStone() + user.getWood();
        int robbedGold;
        int robbedWood;
        int robbedStone;

        if(cityResources < resourcesCapacity){
            robbedGold = user.getGold();
            robbedWood = user.getWood();
            robbedStone = user.getStone();
        } else {
            double goldPercentage = user.getGold() / cityResources;
            double woodPercentage = user.getWood() / cityResources;
            double stonePercentage = user.getStone() / cityResources;
            robbedGold = (int)(goldPercentage * resourcesCapacity);
            robbedWood = (int)(woodPercentage * resourcesCapacity);
            robbedStone = (int)(stonePercentage * resourcesCapacity);
        }

        user.setGold(user.getGold() - robbedGold);
        user.setWood(user.getWood() - robbedWood);
        user.setStone(user.getStone() - robbedStone);

        mission.setStone(robbedStone);
        mission.setWood(robbedWood);
        mission.setGold(robbedGold);
    }

    private List<Mission> getAllStationMissionsOfCity(int city){
        return missionRepository.findByTargetEqualsAndMissionArrivalTimeAfterAndMissionFinishTimeBeforeAndTypeEquals(city, LocalDateTime.now(), LocalDateTime.now(), MISSION_STATION);
    }


    private void createMessages(Mission mission, BattleResult result) {
        Message message = new Message();
        switch (mission.getType()){
            case MISSION_STATION : {
                message.setTitle("Your mission " + mission.getType() + " has reached target" + mission.getTarget());
                message.setContent(message.getTitle() + "at: " + mission.getMissionArrivalTime() + " and will stay there until " + mission.getMissionFinishTime());
                mission.getUser().addMessage(message);

                User userStation = userRepository.findOneByCityPosition(mission.getTarget());
                Message message1 = new Message();
                message1.setTitle("Friendly mission from city: " + mission.getUser().getCityName() + " has arrived to your city");
                message1.setContent(message.getTitle() + "at: " + mission.getMissionArrivalTime() + " and will stay there until " + mission.getMissionFinishTime());
                userStation.addMessage(message1);
                break;
            }
            case MISSION_ATTACK : {
                message.setTitle("Your mission " + mission.getType() + " has reached target" + mission.getTarget());
                message.setContent(message.getTitle() + "at: " + mission.getMissionArrivalTime() + " and has encountered a battle: " + result.getBattleReport());
                mission.getUser().addMessage(message);

                Message messageDefending = new Message();
                message.setTitle("Enemy army has attacked your city");
                message.setContent(message.getTitle() + " with the following result: " + result.getBattleReport());

                User userDefending = userRepository.findOneByCityPosition(mission.getTarget());
                userDefending.addMessage(messageDefending);
                userService.saveUser(userDefending);

                List<Mission> stationMissions = getAllStationMissionsOfCity(mission.getTarget());
                stationMissions.forEach(mission1 -> {
                    Message messageStation = new Message();
                    messageStation.setTitle("Your mission at " + mission1.getTarget() + " has encountered a battle: " + result.getBattleReport());
                    mission1.getUser().addMessage(messageStation);
                    userService.saveUser(mission1.getUser());
                });
            }
        }

        }

}
