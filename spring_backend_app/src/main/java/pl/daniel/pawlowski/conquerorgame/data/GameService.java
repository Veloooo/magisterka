package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.Population;
import pl.daniel.pawlowski.conquerorgame.model.Resources;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;
import pl.daniel.pawlowski.conquerorgame.utils.Cost;
import pl.daniel.pawlowski.conquerorgame.utils.CostsService;

import java.time.LocalDateTime;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class GameService {

    @Autowired
    private CostsService costsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    public User getUser(String userId){
        return userRepository.findOneById(userId);
    }


    public String populationAction(UserAction action){
        Population population = action.getUser().getPopulation();
        if(population.getTotal() < 1)
            return "No free workers available!";
        if(action.getAction() == 1)
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

    public String resourcesAction(UserAction action) throws JsonProcessingException {
        if(action.getAction() == 1){
            Resources resources = mapper.readValue(action.getData(), Resources.class);
            action.getUser().getResources().setFreeWorkers(resources.getFreeWorkers());
            action.getUser().getResources().setGoldmineWorkers(resources.getGoldmineWorkers());
            action.getUser().getResources().setSawmillWorkers(resources.getSawmillWorkers());
            action.getUser().getResources().setStonepitWorkers(resources.getStonepitWorkers());
            userRepository.save(action.getUser());
        } else if(action.getAction() == 2) {
            String data = action.getData();
            if(STONEPIT_INDICATOR.equals(data)) {
                Cost cost = costsService.getCost(STONEPIT_INDICATOR, action.getUser().getResources().getStonepitLvl());
                if(costsService.isOperationPossible(cost, action.getUser()))
                    return build(STONEPIT_INDICATOR, action.getUser(), cost);
            } else if (GOLDMINE_INDICATOR.equals(data)){
                Cost cost = costsService.getCost(GOLDMINE_INDICATOR, action.getUser().getResources().getGoldmineLvl());
                if(costsService.isOperationPossible(cost, action.getUser()))
                    return build(GOLDMINE_INDICATOR, action.getUser(), cost);
            }
            else if(SAWMILL_INDICATOR.equals(data)){
                Cost cost = costsService.getCost(SAWMILL_INDICATOR, action.getUser().getResources().getSawmillLvl());
                if(costsService.isOperationPossible(cost, action.getUser()))
                    return build(GOLDMINE_INDICATOR, action.getUser(), cost);
            }
            else {
                return NO_SUCH_OPERATION_MESSAGE;
            }
        }
        return OPERATION_SUCCESS_MESSAGE;
    }

    private String build(String what, User user, Cost cost ){
        user.setStone(user.getStone() - cost.getStone());
        user.setGold(user.getGold() - cost.getGold());
        user.setWood(user.getWood() - cost.getWood());
        user.setBuildingQueue(what);
        user.setBuildingFinishTime(LocalDateTime.now().plusMinutes(cost.getMinutes()));
        userRepository.save(user);
        return OPERATION_SUCCESS_MESSAGE;
    }
}
