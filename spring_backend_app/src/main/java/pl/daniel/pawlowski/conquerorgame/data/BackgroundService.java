package pl.daniel.pawlowski.conquerorgame.data;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.User;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.BUILDING_INDICATOR;
import static pl.daniel.pawlowski.conquerorgame.utils.Constants.RESEARCH_INDICATOR;


@Service
public class BackgroundService {

    @Autowired
    GameService gameService;

    private List<User> allUsers;

    @PostConstruct
    private void getAllUsers() {
        allUsers = gameService.getAllUsers();
    }

    @Job(name = "The sample job with variable %0", retries = 2)
    public void executeSampleJob() {
        List<User> usersWithQueuedBuildings = gameService.getUsersWithQueuedBuildings();
        usersWithQueuedBuildings.stream()
                .filter(user -> user.getBuildingFinishTime() != null && user.getBuildingFinishTime().isBefore(LocalDateTime.now()))
                .forEach(user -> gameService.finalizeUpgrade(user, BUILDING_INDICATOR));
        List<User> usersWithQueuedResearch = gameService.getUsersWithQueuedResearch();
        usersWithQueuedResearch.stream()
                .filter(user -> user.getResearchFinishTime() != null && user.getResearchFinishTime().isBefore(LocalDateTime.now()))
                .forEach(user -> gameService.finalizeUpgrade(user, RESEARCH_INDICATOR));
    }

    @Job(name = "Job updating resources for all users", retries = 2)
    public void updateResources() {
        allUsers.forEach(user -> gameService.updateResources(user));
    }

}
