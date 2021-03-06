package pl.daniel.pawlowski.conquerorgame.model.battle;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UnitService {

    public static ArrayList<Unit> allUnits;

    public UnitService(){
        allUnits.add(Unit.builder().name("Axeman").amount(1).armour(4).damageMin(5).damageMax(10).healthPoints(20).build());
    }


}
