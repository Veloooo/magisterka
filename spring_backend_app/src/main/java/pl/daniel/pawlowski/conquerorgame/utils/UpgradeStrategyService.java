package pl.daniel.pawlowski.conquerorgame.utils;

import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;
import pl.daniel.pawlowski.conquerorgame.model.strategy.buildings.*;
import pl.daniel.pawlowski.conquerorgame.model.strategy.research.*;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class UpgradeStrategyService {
    public UpgradeStrategy getUpgradeStrategy(String strategyName){
        switch (strategyName) {
            case HALL_INDICATOR: {
                return new HallStrategy();
            }
            case BARRACKS_INDICATOR: {
                return new BarracksStrategy();
            }
            case VAULT_INDICATOR: {
                return new VaultStrategy();
            }
            case GOLDMINE_INDICATOR: {
                return new GoldmineStrategy();
            }
            case WOOD_WAREHOUSE_INDICATOR: {
                return new WoodWarehouseStrategy();
            }
            case SAWMILL_INDICATOR: {
                return new SawmillStrategy();
            }
            case STONE_WAREHOUSE_INDICATOR: {
                return new StoneWarehouseStrategy();
            }
            case STONEPIT_INDICATOR: {
                return new StonepitStrategy();
            }
            case FARM_INDICATOR: {
                return new FarmStrategy();
            }
            case WAR_INSTITUTE_INDICATOR: {
                return new WarInstituteStrategy();
            }
            case RESEARCH_INSTITUTE_INDICATOR: {
                return new ResearchInstituteStrategy();
            }
            case BUILDING_RESEARCH_INDICATOR: {
                return new BuildingStrategy();
            }
            case AGRICULTURE_RESEARCH_INDICATOR: {
                return new AgricultureStrategy();
            }
            case LOGISTICS_RESEARCH_INDICATOR: {
                return new LogisticsStrategy();
            }
            case ARMOUR_RESEARCH_INDICATOR: {
                return new ArmourStrategy();
            }
            case ATTACK_RESEARCH_INDICATOR: {
                return new AttackStrategy();
            }
            case MINING_RESEARCH_INDICATOR: {
                return new MiningStrategy();
            }
            default:
                return new HallStrategy();
        }
    }
}
