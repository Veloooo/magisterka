package pl.daniel.pawlowski.conquerorgame.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.daniel.pawlowski.conquerorgame.data.*;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.UnitService;
import pl.daniel.pawlowski.conquerorgame.repositories.MissionRepository;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MissionRepository.class, ObjectMapper.class, MissionService.class, UserRepository.class, UserService.class, UnitService.class, BattleService.class, HeroService.class, DungeonsService.class})
public class MissionServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    MissionRepository missionRepository;

    @Autowired
    MissionService missionService;

    @Autowired
    UnitService unitService;

    @Autowired
    ObjectMapper mapper;

    private Mission mission;

    @PostConstruct
    public void initializeMission() throws JsonProcessingException {
        Statistics heroStatistics = new Statistics();
        heroStatistics.setCharisma(10);
        heroStatistics.setStrength(10);
        heroStatistics.setIntelligence(20);
        heroStatistics.setAgility(10);

        Dungeon dungeon = new Dungeon();
        dungeon.setCompleted(0);
        dungeon.setLevel(1);

        List<DungeonUnit> dungeonUnits = new ArrayList<>();
        dungeonUnits.add(new DungeonUnit("FrogWarrior", 10));
        dungeonUnits.add(new DungeonUnit("Worgen", 5));
        dungeonUnits.add(new DungeonUnit("Murloc", 3));
        dungeonUnits.add(new DungeonUnit("Hyena", 2));

        dungeon.setDungeonUnits(dungeonUnits);

        Hero hero = new Hero();
        hero.setMainStatistic("Intelligence");
        hero.setLevel(1);
        hero.setHeroClass("Wizard");
        hero.setStatistics(heroStatistics);
        hero.setDungeons(Collections.singletonList(dungeon));

        User user = new User();
        user.addHero(hero);
        user.setFraction("Human");

        mission = new Mission();
        mission.setHero(hero);

        Units units = new Units();
        units.setUnit1(10);
        units.setUnit2(21);
        units.setUnit3(32);
        units.setUnit4(43);
        units.setUnit5(51);
        units.setUnit6(62);

        mission.setUnits(units);
        mission.setTarget(2);
        user.addMission(mission);
    }

    @Test
    public void getUnitsFromMissionTest() {
        List<Unit> units = missionService.getBattleUnits(mission.getUser().getFraction(), mission.getUnits(), mission.getHero());
        Assert.assertEquals(7, units.size());
    }
    @Test
    public void getDefendingUnitsFromCityTest() throws JsonProcessingException {
        List<List<Unit>> units = missionService.getDefendingUnitsFromCity(3);
    }
    @Test
    public void getDungeonsUnits() throws JsonProcessingException {
        System.out.println(mapper.writeValueAsString(unitService.getDungeonUnits()));
    }



}
