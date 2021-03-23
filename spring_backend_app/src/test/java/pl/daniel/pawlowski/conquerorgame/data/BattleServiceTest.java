package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.daniel.pawlowski.conquerorgame.model.battle.BattleResult;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BattleService.class, ObjectMapper.class})
public class BattleServiceTest {

    @Autowired
    BattleService battleService;

    @Autowired
    ObjectMapper mapper;

    List<Unit> dungeonUnits;

    List<Unit> humanUnits1;

    List<Unit> humanUnits2;

    List<Unit> humanUnits3;


    @Before
    public void initializeUnits() throws URISyntaxException, IOException {
        dungeonUnits = mapper.readValue(Paths.get(getClass().getClassLoader().getResource("dungeonUnits.txt").toURI()).toFile(), new TypeReference<List<Unit>>() {
        });
        Assert.assertTrue(dungeonUnits.size() > 0);
        humanUnits1 = mapper.readValue(Paths.get(getClass().getClassLoader().getResource("fractionsUnits.txt").toURI()).toFile(), new TypeReference<List<Unit>>() {
        });
        Assert.assertTrue(humanUnits1.size() > 0);
        humanUnits2 = new ArrayList<>();
        humanUnits3 = new ArrayList<>();
        humanUnits1.forEach(
                unit -> {
                    humanUnits2.add(new Unit(unit));
                    humanUnits3.add(new Unit(unit));
                }
        );
        humanUnits2.forEach(unit -> {
            unit.setAmount(unit.getAmount() + 5);
            unit.setArmyId("Army 2");
        });
        humanUnits3.forEach(unit -> {
            unit.setAmount(unit.getAmount() + 10);
            unit.setArmyId("Army 3");
        });
    }


    @Test
    public void battleStartTest() {
        BattleResult result = battleService.battle(humanUnits1, Collections.singletonList(dungeonUnits));
        Assert.assertEquals(6, result.getAttackingArmyBeginning().size());
        Assert.assertEquals(1, result.getDefendingArmyBeginning().size());
        result.getAttackingArmyBeginning().values().forEach(unit -> Assert.assertTrue(unit > 0));
        result.getDefendingArmyBeginning().values().forEach(units -> units.values().forEach(unit -> Assert.assertTrue(unit > 0)));

    }

    @Test
    public void battleDungeons() {
        System.out.println(battleService.battle(humanUnits3, Arrays.asList(humanUnits2, humanUnits1)));

    }

}
