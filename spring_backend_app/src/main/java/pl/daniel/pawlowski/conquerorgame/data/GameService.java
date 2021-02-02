package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;
import pl.daniel.pawlowski.conquerorgame.utils.Cost;
import pl.daniel.pawlowski.conquerorgame.utils.CostsService;
import pl.daniel.pawlowski.conquerorgame.utils.UpgradeStrategyService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class GameService {

    @Autowired
    private CostsService costsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UpgradeStrategyService upgradeStrategyService;

    private List<User> allUsers;

    private List<User> usersWithQueuedBuildings = new ArrayList<>();

    private List<User> usersWithQueuedResearch = new ArrayList<>();


    List<User> getAllUsers() {
        if (allUsers == null)
            allUsers = userRepository.findAll();
        return allUsers;
    }

    List<User> getUsersWithQueuedBuildings() {
        return usersWithQueuedBuildings;
    }

    List<User> getUsersWithQueuedResearch() {
        return usersWithQueuedResearch;
    }

    private void addUserToQueue(User user, List<User> queue) {
        queue.add(user);
    }

    private void removeUserFromQueue(User user, List<User> queue) {
        queue.removeIf(userList -> userList.getId().equals(user.getId()));
    }

    public User getUser(String userId) {
        return userRepository.findOneById(userId);
    }

    public void addUser(User user) {
        this.allUsers.add(user);
    }

    public void updateResources(User user) {
        user.setWood(user.getWood() + user.getWoodProduction());
        user.setGold(user.getGold() + user.getGoldProduction());
        user.setStone(user.getStone() + user.getStoneProduction());
        userRepository.save(user);
    }


    public String populationAction(UserAction action) {
        Population population = action.getUser().getPopulation();
        if (population.getTotal() < 1)
            return "No free workers available!";
        if (action.getAction() == 1)
            population.setBuilder(population.getBuilder() + 1);
        else if (action.getAction() == 2)
            population.setScientist(population.getScientist() + 1);
        else if (action.getAction() == 3) {
            population.setResources(population.getResources() + 1);
        }
        population.setTotal(population.getTotal() - 1);
        userRepository.save(action.getUser());
        return OPERATION_SUCCESS_MESSAGE;
    }


    public String upgradeAction(UserAction action) {
        UpgradeStrategy strategy = upgradeStrategyService.getUpgradeStrategy(action.getData());
        strategy.setUser(action.getUser());
        Cost cost = costsService.getCost(action.getData(), strategy.getLevel());
        if (action.getAction() == 1) {
            if (costsService.isOperationPossible(cost, action.getUser()))
                return upgrade(action.getData(), action.getUser(), cost, strategy);
            else
                return NOT_ENOUGH_RESOURCES_MESSAGE;
        } else if (action.getAction() == 2) {
            return cancelUpgrade(action.getUser(), cost, strategy);
        }
        return OPERATION_SUCCESS_MESSAGE;
    }

    public String resourcesAction(UserAction action) throws IOException {
        if (action.getAction() == 3) {
            setWorkers(action.getUser(), action.getData());
        } else if (action.getAction() == 2 || action.getAction() == 1) {
            upgradeAction(action);
        }
        return OPERATION_SUCCESS_MESSAGE;
    }

    private void setWorkers(User user, String data) throws JsonProcessingException {
        Resources resources = mapper.readValue(data, Resources.class);
        user.getResources().setFreeWorkers(resources.getFreeWorkers());
        user.getResources().setGoldmineWorkers(resources.getGoldmineWorkers());
        user.getResources().setSawmillWorkers(resources.getSawmillWorkers());
        user.getResources().setStonepitWorkers(resources.getStonepitWorkers());
        user.setWoodProduction(resources.getSawmillWorkers() * 6);
        user.setGoldProduction(resources.getGoldmineWorkers() * (2 + user.getResearch().getMining()));
        user.setStoneProduction(resources.getStonepitWorkers() * (4 + user.getResearch().getMining()));
        userRepository.save(user);
    }

    private String upgrade(String what, User user, Cost cost, UpgradeStrategy strategy) {
        user.setStone(user.getStone() - cost.getStone());
        user.setGold(user.getGold() - cost.getGold());
        user.setWood(user.getWood() - cost.getWood());
        strategy.setQueue(what, LocalDateTime.now(), LocalDateTime.now().plusMinutes(cost.getMinutes()));
        if (cost.getType().equals(BUILDING_INDICATOR)) {
            addUserToQueue(user, this.usersWithQueuedBuildings);
        } else if (cost.getType().equals(RESEARCH_INDICATOR)) {
            addUserToQueue(user, this.usersWithQueuedResearch);
        }
        userRepository.save(user);
        return OPERATION_SUCCESS_MESSAGE;
    }

    private String cancelUpgrade(User user, Cost cost, UpgradeStrategy strategy) {
        user.setStone(user.getStone() + cost.getStone());
        user.setGold(user.getGold() + cost.getGold());
        user.setWood(user.getWood() + cost.getWood());
        strategy.clearQueue();
        if (cost.getType().equals(BUILDING_INDICATOR)) {
            removeUserFromQueue(user, this.usersWithQueuedBuildings);
        } else if (cost.getType().equals(RESEARCH_INDICATOR)) {
            removeUserFromQueue(user, this.usersWithQueuedResearch);
        }
        userRepository.save(user);
        return OPERATION_SUCCESS_MESSAGE;
    }

    void finalizeUpgrade(User user, String upgrading) {
        String queue = "";
        if(RESEARCH_INDICATOR.equals(upgrading))
            queue = user.getResearchQueue();
        else if (BUILDING_INDICATOR.equals(upgrading))
            queue = user.getBuildingQueue();
        UpgradeStrategy strategy = upgradeStrategyService.getUpgradeStrategy(queue);
        strategy.setUser(user);
        strategy.upgrade();
        strategy.clearQueue();
        userRepository.save(user);
    }


}
