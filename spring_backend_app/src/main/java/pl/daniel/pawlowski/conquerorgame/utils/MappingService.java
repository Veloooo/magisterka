package pl.daniel.pawlowski.conquerorgame.utils;

import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.Mission;
import pl.daniel.pawlowski.conquerorgame.model.MissionJSON;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;

@Service
public class MappingService {

    public Mission mapMissionJsonToDTO(MissionJSON missionJSON, int playerPosition){
        Mission mission = new Mission();

        mission.setHero(missionJSON.getHero());
        mission.setType(missionJSON.getType());
        mission.setUnits(missionJSON.getUnits());
        mission.setMissionArrivalTime(calculateTime(playerPosition, missionJSON.getTarget()));
        mission.setMissionFinishTime(mission.getMissionArrivalTime().plusHours(missionJSON.getTime()));
        mission.setTarget(missionJSON.getTarget());

        return mission;
    }

    private LocalDateTime calculateTime(int startPosition, int finishPosition){
        if(finishPosition == 0 || startPosition == 0) {
            return LocalDateTime.now().plusMinutes(30);
        }
        else {
            double xStartPosition = Math.ceil(startPosition/4);
            double yStartPosition = (startPosition - 4 * (xStartPosition- 1));
            double xFinishPosition = Math.ceil(finishPosition/4);
            double yFinishPosition = (finishPosition - 4 * (xFinishPosition- 1));
            double distance = Point2D.distance(xStartPosition, yStartPosition, xFinishPosition, yFinishPosition);
            return LocalDateTime.now().plusMinutes((int)(distance * 30));
        }
    }
}
