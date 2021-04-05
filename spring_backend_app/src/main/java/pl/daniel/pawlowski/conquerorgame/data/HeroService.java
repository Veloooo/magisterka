package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.UnitService;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.DefaultAttackStrategy;
import pl.daniel.pawlowski.conquerorgame.model.battle.receive.DefaultReceiveDamageStrategy;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.repositories.HeroesRepository;

import java.util.HashMap;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class HeroService {

    @Autowired
    HeroesRepository heroesRepository;

    @Autowired
    UnitService unitService;

    public Hero getHeroByHeroId(long id){
        return heroesRepository.getOne(id);
    }

    public String getHeroMainStatistic(String heroClass){
        if(HERO_CLASS_ANUBIS.equals(heroClass) || HERO_CLASS_PALADIN.equals(heroClass))
            return STATISTIC_CHARISMA;
        else if(HERO_CLASS_ASSASSIN.equals(heroClass) || HERO_CLASS_DEMONIC_ASSASSIN.equals(heroClass))
            return STATISTIC_AGILITY;
        else if(HERO_CLASS_DARK_KNIGHT.equals(heroClass) || HERO_CLASS_WARRIOR.equals(heroClass))
            return STATISTIC_STRENGTH;
        else return STATISTIC_INTELLIGENCE;
    }

    public int getHeroMainStatisticValue(Hero hero){
        switch(hero.getMainStatistic()){
            case STATISTIC_INTELLIGENCE: return hero.getStatistics().getIntelligence();
            case STATISTIC_AGILITY: return hero.getStatistics().getAgility();
            case STATISTIC_CHARISMA: return hero.getStatistics().getCharisma();
            case STATISTIC_STRENGTH: return hero.getStatistics().getStrength();
            default: return 0;
        }
    }


    public Unit createHeroUnit(Hero hero, String armyId){
        hero.removeItem(new Item());
        return Unit.builder()
                .amount(1)
                .name(hero.getHeroClass())
                .armyId(armyId)
                .armour(hero.getStatistics().getAgility() / 4)
                .healthPoints(hero.getStatistics().getStrength() * 10)
                .damageMin(getHeroMainStatisticValue(hero))
                .damageMax(getHeroMainStatisticValue(hero) * 1.5)
                .speed(hero.getStatistics().getAgility())
                .mana(hero.getStatistics().getIntelligence() * 5)
                .spellDamage(hero.getStatistics().getIntelligence() * 2)
                .receiveDamageStrategy(new DefaultReceiveDamageStrategy())
                .unitAttackStrategy(new DefaultAttackStrategy())
                .build();
    }

    public Hero createHero(UserAction action, Dungeon dungeon){
            Hero hero = new Hero();
            action.getUser().addHero(hero);
            Statistics statistics = new Statistics();
            hero.setHeroClass(action.getData());
            hero.setLevel(1);
            hero.setExp(0);
            hero.setMainStatistic(getHeroMainStatistic(hero.getHeroClass()));
            statistics.setHero(hero);
            statistics.setAgility(10);
            statistics.setStrength(20);
            statistics.setIntelligence(15);
            statistics.setCharisma(13);
            statistics.setSkillPoints(10);
            hero.setStatistics(statistics);
            hero.addDungeon(dungeon);

            hero.addItem(createItem("czapa puchata", "Head", 1));
            hero.addItem(createItem("Skorzana kurtka", "Body", 1));
            hero.addItem(createItem("trzypasowy dres", "Legs", 1));
            hero.addItem(createItem("Sandaly judasza", "Boots", 1));
            hero.addItem(createItem("Zapasowa czapa puchata", "Head", 0));
            hero.addItem(createItem("Zapasowa Skorzana kurtka", "Body", 0));
            hero.addItem(createItem("Zapasowa trzypasowy dres", "Legs", 0));
            hero.addItem(createItem("Zapasowe Sandaly judasza", "Boots", 0));

            return hero;
    }


    private Item createItem(String name, String part, int isWorn) {
        Item item = new Item();
        item.setName(name);
        item.setPart(part);
        item.setIsWorn(isWorn);
        item.setLevelRequired(1);
        ItemStatistics itemStatistics = new ItemStatistics();
        itemStatistics.setItem(item);
        item.setStatistics(itemStatistics);

        itemStatistics.setAgility(1);
        itemStatistics.setStrength(1);
        itemStatistics.setIntelligence(1);
        itemStatistics.setCharisma(1);

        return item;
    }

    public void updateHeroExp(Hero hero, HashMap<String, Integer> units, String fraction, double multiplier){
        units.keySet().forEach(
                unit -> hero.addExp((int) (multiplier * unitService.getExpByUnitNameAndCount(unit, units.get(unit), fraction)))
        );
        updateHeroLevel(hero);
        heroesRepository.save(hero);
    }

    private void updateHeroLevel(Hero hero){
        int currentLevel = hero.getLevel();
        int expLevel = 0;
        int nextLevelExp = 1000;
        int freeExp = hero.getExp();
        while(freeExp > nextLevelExp){
            freeExp -= nextLevelExp;
            nextLevelExp *= 1.4;
            expLevel++;
        }
        int levelPromoted = expLevel - currentLevel;
        hero.getStatistics().setSkillPoints(hero.getStatistics().getSkillPoints() + levelPromoted * 3);
    }
}
