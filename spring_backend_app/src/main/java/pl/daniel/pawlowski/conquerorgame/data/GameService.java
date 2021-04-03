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
import pl.daniel.pawlowski.conquerorgame.repositories.HeroesRepository;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;
import pl.daniel.pawlowski.conquerorgame.utils.Cost;
import pl.daniel.pawlowski.conquerorgame.utils.CostsService;
import pl.daniel.pawlowski.conquerorgame.utils.UpgradeStrategyService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private HeroesRepository heroesRepository;

    @Autowired
    private HeroService heroService;

    @Autowired
    private DungeonsService dungeonsService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UpgradeStrategyService upgradeStrategyService;


    List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUser(String userId) {
        return userRepository.findOneById(userId);
    }


    public void updateResources(User user) {
        int newGold = user.getGold() + user.getGoldProduction();
        int newStone = user.getStone() + user.getStoneProduction();
        int newWood = user.getWood() + user.getWoodProduction();

        int goldCapacity = user.getBuildings().getVault() * 500 + 1000;
        int stoneCapacity = user.getBuildings().getStoneWarehouse() * 500 + 1000;
        int woodCapacity = user.getBuildings().getWoodWarehouse() * 500 + 1000;

        user.setGold(newGold >= goldCapacity ? goldCapacity : newGold);
        user.setWood(newWood >= woodCapacity ? woodCapacity : newWood);
        user.setStone(newStone >= stoneCapacity ? stoneCapacity : newStone);

    }


    public String barracksAction(UserAction action) {
        if (Integer.valueOf(action.getData()) < 0) {
            return BAD_REQUEST;
        }
        String unit = UNIT_INDICATOR + action.getAction();
        Cost cost = costsService.getCostOfNumber(unit, Integer.valueOf(action.getData()));
        if (costsService.isOperationPossible(cost, action.getUser())) {
            Units units = action.getUser().getUnits();
            int amount = Integer.parseInt(action.getData());
            switch (action.getAction()) {
                case 1:
                    units.setUnit1(units.getUnit1() + amount);
                    break;
                case 2:
                    units.setUnit2(units.getUnit2() + amount);
                    break;
                case 3:
                    units.setUnit3(units.getUnit3() + amount);
                    break;
                case 4:
                    units.setUnit4(units.getUnit4() + amount);
                    break;
                case 5:
                    units.setUnit5(units.getUnit5() + amount);
                    break;
                case 6:
                    units.setUnit6(units.getUnit6() + amount);
                    break;
            }
            action.getUser().setStone(action.getUser().getStone() - cost.getStone());
            action.getUser().setGold(action.getUser().getGold() - cost.getGold());
            action.getUser().setWood(action.getUser().getWood() - cost.getWood());
            return saveUser(action.getUser()) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
        } else {
            return NOT_ENOUGH_RESOURCES_MESSAGE;
        }
    }

    public String heroAction(UserAction action) throws JsonProcessingException {
        if (action.getAction() == 1) {
            if (action.getUser().getHeroes().size() * 10 <= action.getUser().getBuildings().getHall()) {
                Dungeon dungeon = dungeonsService.createDungeonLevel(1);
                heroService.createHero(action, dungeon);
                saveUser(action.getUser());
            } else
                return TOO_MANY_HEROES_MESSAGE;
        } else if (action.getAction() == 2) {
            return updateHero(action);
        }
        return "";
    }

    private String updateHero(UserAction action) throws JsonProcessingException {
        Hero hero = mapper.readValue(action.getData(), Hero.class);
        Optional<Hero> currentHero = action.getUser().getHeroes().stream().filter(heroList -> heroList.getId() == hero.getId()).findFirst();
        if (!currentHero.isPresent())
            return NO_HERO_FOUND_MESSAGE;
        List<Item> pairedItems = hero.getItems().stream().filter(item -> currentHero.get().getItems().stream().anyMatch(itemHero -> itemHero.getId() == item.getId())).collect(Collectors.toList());
        currentHero.get().getItems().clear();
        pairedItems.forEach(item -> {
            item.setHero(currentHero.get());
            currentHero.get().addItem(item);
        });
        return saveHero(currentHero.get()) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
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

    public List<User> getAllUsersCityInfo() {
        List<User> allUsers = userRepository.findAllByOrderByCityPosition();
        List<User> allUsersCityInfo = new ArrayList<>();
        allUsers.forEach(user -> allUsersCityInfo.add(createUserInfoFromUser(user)));
        return allUsersCityInfo;
    }

    private User createUserInfoFromUser(User user) {
        User userCityInfo = new User();
        userCityInfo.setGold(user.getGold());
        userCityInfo.setWood(user.getWood());
        userCityInfo.setStone(user.getStone());
        userCityInfo.setFraction(user.getFraction());
        userCityInfo.setNick(user.getNick());
        userCityInfo.setCityPosition(user.getCityPosition());
        userCityInfo.setCityName(user.getCityName());
        userCityInfo.setUnits(user.getUnits());
        return userCityInfo;
    }

    String finalizeUpgrade(User user, String upgrading) {
        log.info("Finalizing update of user: " + user + " upgrading: " + upgrading);
        String queue = "";
        if (RESEARCH_INDICATOR.equals(upgrading)) {
            queue = user.getResearchQueue();
        } else if (BUILDING_INDICATOR.equals(upgrading)) {
            queue = user.getBuildingQueue();
        }

        UpgradeStrategy strategy = upgradeStrategyService.getUpgradeStrategy(queue);
        User savingUser = getUser(user.getId());
        strategy.setUser(savingUser);
        strategy.upgrade();
        strategy.clearQueue();
        return saveUser(savingUser) ? OPERATION_SUCCESS_MESSAGE : "ERROR";
    }

    private boolean saveUser(User user) {
        if (user.equals(userRepository.save(user))) {
            log.info("Save operation successful!");
            return true;
        } else {
            log.error("Operation failed!");
            return false;
        }
    }

    private boolean saveHero(Hero hero) {
        if (hero.equals(heroesRepository.save(hero))) {
            log.info("Save operation successful!");
            return true;
        } else {
            log.error("Operation failed!");
            return false;
        }
    }

}
