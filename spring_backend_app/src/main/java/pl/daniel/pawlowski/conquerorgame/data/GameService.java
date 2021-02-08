package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;
import pl.daniel.pawlowski.conquerorgame.utils.Cost;
import pl.daniel.pawlowski.conquerorgame.utils.CostsService;
import pl.daniel.pawlowski.conquerorgame.utils.UpgradeStrategyService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
@Slf4j
@Transactional
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


    List<User> getAllUsers() {
        return userRepository.findAll();
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
            if (costsService.isOperationPossible(cost, action.getUser(), strategy))
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
            return setWorkers(action.getUser(), action.getData());
        } else if (action.getAction() == 2 || action.getAction() == 1) {
            upgradeAction(action);
        }
        return OPERATION_SUCCESS_MESSAGE;
    }

    private String setWorkers(User user, String data) throws JsonProcessingException {
        Resources resources = mapper.readValue(data, Resources.class);
        user.getResources().setFreeWorkers(resources.getFreeWorkers());
        user.getResources().setGoldmineWorkers(resources.getGoldmineWorkers());
        user.getResources().setSawmillWorkers(resources.getSawmillWorkers());
        user.getResources().setStonepitWorkers(resources.getStonepitWorkers());
        user.setWoodProduction(resources.getSawmillWorkers() * 6);
        user.setGoldProduction(resources.getGoldmineWorkers() * (2 + user.getResearch().getMining()));
        user.setStoneProduction(resources.getStonepitWorkers() * (4 + user.getResearch().getMining()));
        return saveUser(user) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    private String upgrade(String what, User user, Cost cost, UpgradeStrategy strategy) {
        user.setStone(user.getStone() - cost.getStone());
        user.setGold(user.getGold() - cost.getGold());
        user.setWood(user.getWood() - cost.getWood());
        strategy.setUser(user);
        strategy.setQueue(what, LocalDateTime.now(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(cost.getMinutes() + 1));
        return saveUser(user) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    private String cancelUpgrade(User user, Cost cost, UpgradeStrategy strategy) {
        user.setStone(user.getStone() + cost.getStone());
        user.setGold(user.getGold() + cost.getGold());
        user.setWood(user.getWood() + cost.getWood());
        strategy.clearQueue();
        return saveUser(user) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    String finalizeUpgrade(User user, String upgrading) {
        log.info("Finalizing update of user: " + user + " upgrading: " + upgrading);
        String queue = "";
        if(RESEARCH_INDICATOR.equals(upgrading)) {
            queue = user.getResearchQueue();
        }
        else if (BUILDING_INDICATOR.equals(upgrading)) {
            queue = user.getBuildingQueue();
        }

        UpgradeStrategy strategy = upgradeStrategyService.getUpgradeStrategy(queue);
        User savingUser = getUser(user.getId());
        strategy.setUser(savingUser);
        strategy.upgrade();
        strategy.clearQueue();
        return saveUser(savingUser) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    private boolean saveUser(User user){
        if(user.equals(userRepository.save(user))) {
            log.info("Save operation successful!");
            return true;
        }
        else {
            log.error("Operation failed!");
            return false;
        }
    }

}
