package pl.daniel.pawlowski.conquerorgame.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.Mission;
import pl.daniel.pawlowski.conquerorgame.model.MissionJSON;
import pl.daniel.pawlowski.conquerorgame.model.Units;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.repositories.MissionsRepository;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MissionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MissionsRepository missionsRepository;

    public Mission mapMissionJsonToDTO(MissionJSON missionJSON, int playerPosition) {
        Mission mission = new Mission();

        mission.setHero(missionJSON.getHero());
        mission.setType(missionJSON.getType());
        mission.setUnits(missionJSON.getUnits());
        mission.setMissionArrivalTime(calculateTime(playerPosition, missionJSON.getTarget(), null));
        mission.setMissionFinishTime(mission.getMissionArrivalTime().plusMinutes(missionJSON.getTime()));
        mission.setMissionReturnTime(calculateTime(playerPosition, missionJSON.getTarget(), mission.getMissionFinishTime()));
        mission.setTarget(missionJSON.getTarget());

        return mission;
    }

    public void performArrivalAction(Mission mission) {

    }

    public void performFinishAction(Mission mission) {

    }

    public void deleteFinishedMissions(){
        List<Mission> finishedMissions = missionsRepository.findByCalculations(3);
        for(Mission m : finishedMissions){
            User user = m.getUser();
            user.removeMission(m);
            userRepository.save(user);
        }
    }

    public void performReturnAction(Mission mission, User user) {
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
        userRepository.save(user);
    }

    private LocalDateTime calculateTime(int startPosition, int finishPosition, LocalDateTime startDateTime) {
        LocalDateTime calcStartDateTime;
        if (startDateTime != null)
            calcStartDateTime = startDateTime;
        else
            calcStartDateTime = LocalDateTime.now();

        if (finishPosition == 0) {
            return calcStartDateTime.plusMinutes(1);
        } else {
            double xStartPosition = Math.ceil(startPosition / 4);
            double yStartPosition = (startPosition - 4 * (xStartPosition - 1));
            double xFinishPosition = Math.ceil(finishPosition / 4);
            double yFinishPosition = (finishPosition - 4 * (xFinishPosition - 1));
            double distance = Point2D.distance(xStartPosition, yStartPosition, xFinishPosition, yFinishPosition);
            return calcStartDateTime.plusMinutes((int) (distance * 1));
        }
    }

}
