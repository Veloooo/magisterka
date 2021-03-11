package pl.daniel.pawlowski.conquerorgame.model.battle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
@Data
public class BattleResult {

    private HashMap<String, Integer> attackingArmyBeginning;
    private List<HashMap<String, Integer>> defendingArmyBeginning;

    private HashMap<String, Integer> attackingArmyEnd;
    private List<HashMap<String, Integer>> defendingArmyEnd;

    private HashMap<String, Integer> attackingArmyLoss;
    private List<HashMap<String, Integer>> defendingArmyLoss;



}
