package pl.daniel.pawlowski.conquerorgame.data;

import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.daniel.pawlowski.conquerorgame.model.User;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.BUILDING_INDICATOR;
import static pl.daniel.pawlowski.conquerorgame.utils.Constants.RESEARCH_INDICATOR;


@Service
@Transactional
@Slf4j
public class BackgroundService {

    @Autowired
    GameService gameService;

    @Autowired
    MissionService missionService;

    private List<User> allUsers;


    @PostConstruct
    private void getAllUsers() {
        allUsers = gameService.getAllUsers();
    }


    @Job(name = "Job that performs scheduled actions", retries = 2)
    public void performScheduledActions() {
        getAllUsers();
        log.info("Updating resources for all users");
        allUsers.forEach(user -> gameService.updateResources(user));
        log.info("Executing building upgrades");
        allUsers.stream()
                .filter(user -> user.getBuildingFinishTime() != null && user.getBuildingFinishTime().isBefore(LocalDateTime.now()))
                .forEach(user -> gameService.finalizeUpgrade(user, BUILDING_INDICATOR));
        allUsers.stream()
                .filter(user -> user.getResearchFinishTime() != null && user.getResearchFinishTime().isBefore(LocalDateTime.now()))
                .forEach(user -> gameService.finalizeUpgrade(user, RESEARCH_INDICATOR));
        allUsers.forEach(user -> user.getMissions().stream()
                .filter(mission -> (mission.getCalculations() == 0 && mission.getMissionArrivalTime().isBefore(LocalDateTime.now()))
                || (mission.getCalculations() == 1 && mission.getMissionFinishTime().isBefore(LocalDateTime.now()))
                || (mission.getCalculations() == 2 && mission.getMissionReturnTime().isBefore(LocalDateTime.now())))
                .forEach(mission -> missionService.calculateMission(mission, user)));
        missionService.deleteFinishedMissions();
    }
}
