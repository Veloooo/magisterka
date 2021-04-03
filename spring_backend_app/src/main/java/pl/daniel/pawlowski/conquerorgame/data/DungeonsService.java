package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.UnitService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class DungeonsService {

    @Autowired
    UnitService unitService;

    ItemRarity legendary = new ItemRarity(0, "Legendary");
    ItemRarity epic = new ItemRarity(10, "Epic");
    ItemRarity rare = new ItemRarity(30, "Rare");
    ItemRarity common = new ItemRarity(100, "Common");

    public Dungeon createDungeonLevel(int level) {
        Dungeon dungeon = new Dungeon();

        dungeon.setLevel(level);
        dungeon.setCompleted(0);

        dungeon.setReward(getItemReward(level));
        setDungeonUnits(dungeon, level);

        return dungeon;
    }

    private Item getItemReward(int level){
        Item item = new Item();

        String rarity = generateItemRarity().getName();
        String part = generatePart();

        item.setName(rarity + part);
        item.setPart(part);
        item.setIsWorn(0);
        item.setLevelRequired(level);

        ItemStatistics itemStatistics = new ItemStatistics();
        itemStatistics.setItem(item);
        item.setStatistics(itemStatistics);

        itemStatistics.setAgility(1 + level);
        itemStatistics.setStrength(1 + level);
        itemStatistics.setIntelligence(1 + level);
        itemStatistics.setCharisma(1 + level);

        return item;
    }

    public List<Unit> getLevelUnits(Hero hero) {
        List<Unit> units = new ArrayList<>();
        Dungeon dungeonLevel = hero.getDungeons().stream().filter(dungeon -> dungeon.getCompleted() == 0).findFirst().get();
        dungeonLevel.getDungeonUnits().forEach(unit -> {
            Unit dungeonUnit = new Unit(unit.getName(), unit.getAmount());
            dungeonUnit.setArmyId("Dungeon");
            units.add(dungeonUnit);
        });
        return units;
    }

    private void setDungeonUnits(Dungeon dungeon, int level){
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int dungeonToughness = Objects.requireNonNull(random).nextInt(level * 15 - level * 10) + level * 10;
        int currentToughness = 0;
        int maxLevelUnit = (int) Math.ceil((double) level / 4);

        List<Unit> possibleUnits = unitService.getUnitsOfMaxLevel(maxLevelUnit);
        Unit[] selectedUnits = new Unit[4];

        for(int i = 0 ; i < 4 ; i++){
            int unit = random.nextInt(possibleUnits.size());
            selectedUnits[i] = possibleUnits.get(unit);
            selectedUnits[i].setAmount(1);
            currentToughness += selectedUnits[i].getLevel();
            possibleUnits.remove(possibleUnits.get(unit));
        }

        while(currentToughness != dungeonToughness){
            int unit = random.nextInt(4);
            if(currentToughness + selectedUnits[unit].getLevel() <= dungeonToughness) {
                selectedUnits[unit].setAmount(selectedUnits[unit].getAmount() + selectedUnits[unit].getLevel());
                currentToughness += selectedUnits[unit].getLevel();
            }
        }

        for(Unit unit : selectedUnits){
            dungeon.addDungeonUnit(new DungeonUnit(unit.getName(), unit.getAmount()));
        }
    }

    private ItemRarity generateItemRarity(){
        int itemRarityValue = (int) (Math.random() * 100);
        if(itemRarityValue == legendary.getValueTreshold())
            return legendary;
        else if(itemRarityValue < epic.getValueTreshold())
            return epic;
        else if(itemRarityValue < rare.getValueTreshold())
            return rare;
        else return common;
    }

    private String generatePart(){
        int partValue = (int) (Math.random() * 4);
        String[] parts = new String[]{"Head", "Body", "Legs", "Boots"};
        return parts[partValue];
    }


    private class ItemRarity{
        int valueTreshold;
        String name;

        public ItemRarity(int valueTreshold, String name) {
            this.valueTreshold = valueTreshold;
            this.name = name;
        }

        public int getValueTreshold() {
            return valueTreshold;
        }

        public void setValueTreshold(int valueTreshold) {
            this.valueTreshold = valueTreshold;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
