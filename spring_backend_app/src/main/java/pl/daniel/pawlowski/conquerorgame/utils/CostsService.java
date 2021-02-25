package pl.daniel.pawlowski.conquerorgame.utils;

import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;

import java.util.ArrayList;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class CostsService {

    private List<Cost> allCosts = new ArrayList<>();

    public CostsService() {
        allCosts.add(Cost.builder().name(SAWMILL_INDICATOR).type(BUILDING_INDICATOR).gold(175).stone(50).wood(20).minutes(10).build());
        allCosts.add(Cost.builder().name(GOLDMINE_INDICATOR).type(BUILDING_INDICATOR).gold(225).stone(10).wood(150).minutes(10).build());
        allCosts.add(Cost.builder().name(STONEPIT_INDICATOR).type(BUILDING_INDICATOR).gold(200).stone(5).wood(180).minutes(10).build());
        allCosts.add(Cost.builder().name(BUILDING_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(100).stone(200).wood(250).minutes(13).build());
        allCosts.add(Cost.builder().name(AGRICULTURE_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(150).stone(100).wood(200).minutes(9).build());
        allCosts.add(Cost.builder().name(LOGISTICS_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(120).stone(90).wood(150).minutes(8).build());
        allCosts.add(Cost.builder().name(ATTACK_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(140).stone(170).wood(220).minutes(11).build());
        allCosts.add(Cost.builder().name(ARMOUR_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(150).stone(190).wood(160).minutes(11).build());
        allCosts.add(Cost.builder().name(MINING_RESEARCH_INDICATOR).type(RESEARCH_INDICATOR).gold(240).stone(240).wood(240).minutes(14).build());
        allCosts.add(Cost.builder().name(HALL_INDICATOR).type(BUILDING_INDICATOR).gold(300).stone(300).wood(300).minutes(20).build());
        allCosts.add(Cost.builder().name(BARRACKS_INDICATOR).type(BUILDING_INDICATOR).gold(140).stone(220).wood(200).minutes(14).build());
        allCosts.add(Cost.builder().name(WAR_INSTITUTE_INDICATOR).type(BUILDING_INDICATOR).gold(170).stone(160).wood(170).minutes(13).build());
        allCosts.add(Cost.builder().name(RESEARCH_INSTITUTE_INDICATOR).type(BUILDING_INDICATOR).gold(200).stone(120).wood(120).minutes(12).build());
        allCosts.add(Cost.builder().name(FARM_INDICATOR).type(BUILDING_INDICATOR).gold(100).stone(200).wood(200).minutes(8).build());
        allCosts.add(Cost.builder().name(VAULT_INDICATOR).type(BUILDING_INDICATOR).gold(200).stone(100).wood(100).minutes(5).build());
        allCosts.add(Cost.builder().name(STONE_WAREHOUSE_INDICATOR).type(BUILDING_INDICATOR).gold(100).stone(200).wood(100).minutes(5).build());
        allCosts.add(Cost.builder().name(WOOD_WAREHOUSE_INDICATOR).type(BUILDING_INDICATOR).gold(100).stone(100).wood(200).minutes(5).build());
        allCosts.add(Cost.builder().name(WALL_INDICATOR).type(BUILDING_INDICATOR).gold(150).stone(300).wood(150).minutes(13).build());
        allCosts.add(Cost.builder().name(GUARD_TOWERS_INDICATOR).type(BUILDING_INDICATOR).gold(150).stone(350).wood(150).minutes(10).build());
        allCosts.add(Cost.builder().name(MOAT_INDICATOR).type(BUILDING_INDICATOR).gold(220).stone(200).wood(240).minutes(11).build());
        allCosts.add(Cost.builder().name(UNIT1_INDICATOR).gold(100).stone(100).wood(100).build());
        allCosts.add(Cost.builder().name(UNIT2_INDICATOR).gold(200).stone(200).wood(200).build());
        allCosts.add(Cost.builder().name(UNIT3_INDICATOR).gold(300).stone(300).wood(300).build());
        allCosts.add(Cost.builder().name(UNIT4_INDICATOR).gold(400).stone(400).wood(400).build());
        allCosts.add(Cost.builder().name(UNIT5_INDICATOR).gold(500).stone(500).wood(500).build());
        allCosts.add(Cost.builder().name(UNIT6_INDICATOR).gold(600).stone(600).wood(600).build());
    }

    public Cost getCost(String what, int level) {
        Cost cost = allCosts.stream().filter(e -> e.getName().equals(what)).findFirst().orElse(null);
        if(cost == null)
            return null;
        else
            return cost.costOfLevel(level, cost.getType(), cost.getName());
    }
    public Cost getCostOfNumber(String what, int number) {
        Cost cost = allCosts.stream().filter(e -> e.getName().equals(what)).findFirst().orElse(null);
        if(cost == null)
            return null;
        else
            return cost.costOfNumber(number, cost.getName());
    }

    public boolean isOperationPossible(Cost cost, User user){
        return cost.getGold() <= user.getGold() && cost.getStone() <= user.getStone() && cost.getWood() <= user.getWood();
    }

}
