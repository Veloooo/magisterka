package pl.daniel.pawlowski.conquerorgame.model.battle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.Units;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UnitService {

    @Autowired
    ObjectMapper mapper;

    private Map<String, List<Unit>> fractionsUnit;
    private List<Unit> dungeonUnits = new ArrayList<>();

    public UnitService() {
    }

    @PostConstruct
    void initializeUnits(){
        try {
            fractionsUnit = mapper.readValue(Paths.get(getClass().getClassLoader().getResource("fractionsUnits.txt").toURI()).toFile(), new TypeReference<Map<String, List<Unit>>>(){});
            dungeonUnits = mapper.readValue(Paths.get(getClass().getClassLoader().getResource("dungeonsUnits.txt").toURI()).toFile(), new TypeReference<List<Unit>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<Unit> getFractionUnits(String fraction) {
        return fractionsUnit.get(fraction);
    }

    public Map<String, List<Unit>> getFractionsUnit() {
        return this.fractionsUnit;
    }

    public List<Unit> getDungeonUnits() {
        return dungeonUnits;
    }

    public Unit getDungeonUnitByName(String name){
        return dungeonUnits.stream().filter(unit -> name.equals(unit.getName())).findFirst().get();
    }

    public int[] getUnitsAmountFromDatabaseUnits(Units units) {
        int[] amount = new int[6];
        amount[0] = units.getUnit1();
        amount[1] = units.getUnit2();
        amount[2] = units.getUnit3();
        amount[3] = units.getUnit4();
        amount[4] = units.getUnit5();
        amount[5] = units.getUnit6();
        return amount;
    }
    /*

     */
}
