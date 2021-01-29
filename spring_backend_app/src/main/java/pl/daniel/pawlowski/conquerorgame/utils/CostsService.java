package pl.daniel.pawlowski.conquerorgame.utils;

import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class CostsService {

    private List<Cost> allCosts = new ArrayList<>();

    public CostsService() {
        allCosts.add(Cost.builder().name("Sawmill").type("Building").gold(175).stone(50).wood(20).minutes(10).build());
        allCosts.add(Cost.builder().name("Goldmine").type("Building").gold(225).stone(10).wood(150).minutes(10).build());
        allCosts.add(Cost.builder().name("Stonepit").type("Building").gold(200).stone(5).wood(180).minutes(10).build());
    }

    public Cost getCost(String what, int level) {
        Cost cost = allCosts.stream().filter(e -> e.getName().equals(what)).findFirst().orElse(null);
        if(cost == null)
            return null;
        else
            return cost.costOfLevel(level);
    }

    public boolean isOperationPossible(Cost cost, User user){
        return cost.getGold() <= user.getGold() && cost.getStone() <= user.getStone() && cost.getWood() <= user.getWood();
    }

}
