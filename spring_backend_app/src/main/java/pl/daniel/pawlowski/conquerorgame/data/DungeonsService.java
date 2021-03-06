package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.Dungeon;
import pl.daniel.pawlowski.conquerorgame.model.DungeonUnit;
import pl.daniel.pawlowski.conquerorgame.model.Item;
import pl.daniel.pawlowski.conquerorgame.model.ItemStatistics;

@Service
public class DungeonsService {

    public Dungeon createDungeonLevel(int level) {
        Dungeon dungeon = new Dungeon();

        dungeon.setLevel(level);
        dungeon.setCompleted(0);

        dungeon.setReward(getItemReward(level));

        dungeon.addDungeonUnit(new DungeonUnit("Ogre", 10));
        dungeon.addDungeonUnit(new DungeonUnit("FrogWarrior", 30));
        dungeon.addDungeonUnit(new DungeonUnit("Murloc", 14));
        dungeon.addDungeonUnit(new DungeonUnit("Hydra", 13));

        return dungeon;
    }

    private Item getItemReward(int level){
        Item item = new Item();
        item.setName("Retarded name");
        item.setPart("Legs");
        item.setIsWorn(0);
        item.setLevelRequired(1);
        ItemStatistics itemStatistics = new ItemStatistics();
        itemStatistics.setItem(item);
        item.setStatistics(itemStatistics);

        itemStatistics.setAgility(1 + level);
        itemStatistics.setStrength(1 + level);
        itemStatistics.setIntelligence(1 + level);
        itemStatistics.setCharisma(1 + level);

        return item;
    }
}
