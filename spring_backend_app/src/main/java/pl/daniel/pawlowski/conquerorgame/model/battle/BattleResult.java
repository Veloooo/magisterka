package pl.daniel.pawlowski.conquerorgame.model.battle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.ATTACKING_ARMY_INDICATOR;
import static pl.daniel.pawlowski.conquerorgame.utils.Constants.DEFENDING_ARMY_INDICATOR;


@Getter
@Setter
@Data
public class BattleResult {

    private String winner;
    private String attackingArmyId;
    private List<String> defendingArmiesIds = new ArrayList<>();

    private HashMap<String, Integer> attackingArmyBeginning;
    private HashMap<String, HashMap<String, Integer>> defendingArmyBeginning;

    private HashMap<String, Integer> attackingArmyEnd = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> defendingArmyEnd = new HashMap<>();

    private HashMap<String, Integer> attackingArmyLoss;
    private HashMap<String, HashMap<String, Integer>> defendingArmyLoss;

    private String battleReport;

    public void calculateLoss() {
        if (attackingArmyEnd.size() > 0) {
            winner = ATTACKING_ARMY_INDICATOR;
            attackingArmyLoss = new HashMap<>();
            attackingArmyBeginning.keySet().forEach(unit -> {
                int loss = attackingArmyEnd.get(unit) != null ? attackingArmyBeginning.get(unit) - attackingArmyEnd.get(unit) : attackingArmyBeginning.get(unit);
                attackingArmyLoss.put(unit, loss);
            });
        } else {
            attackingArmyLoss = attackingArmyBeginning;
            winner = DEFENDING_ARMY_INDICATOR;
        }
        defendingArmyLoss = new HashMap<>();
        defendingArmyBeginning.keySet().forEach(army -> {
            if (defendingArmyEnd.get(army) != null) {
                if (!defendingArmyLoss.keySet().contains(army))
                    defendingArmyLoss.put(army, new HashMap<>());
                defendingArmyBeginning.get(army).keySet().forEach(unit -> {
                    int loss = defendingArmyEnd.get(army).get(unit) != null ? defendingArmyBeginning.get(army).get(unit) - defendingArmyEnd.get(army).get(unit) : defendingArmyBeginning.get(army).get(unit);
                    defendingArmyLoss.get(army).put(unit, loss);
                });
            } else
                defendingArmyLoss.put(army, defendingArmyBeginning.get(army));
        });
        setBattleReport();
    }

    private void setBattleReport() {
        StringBuilder battleReport = new StringBuilder();
        battleReport.append("Winner of battle is ").append(winner).append("\n");
        battleReport.append("Attacking army loss: ");
        attackingArmyLoss.keySet().forEach(unit -> battleReport.append(" ").append(unit).append(":").append(attackingArmyLoss.get(unit)));
        battleReport.append("\nDefending armies loss: ");
        defendingArmyLoss.keySet().forEach(army -> {
            battleReport
                    .append("Army ")
                    .append(army);
            defendingArmyLoss.get(army)
                    .keySet()
                    .forEach(unit -> battleReport.append(" ")
                            .append(unit)
                            .append(":")
                            .append(defendingArmyLoss.get(army).get(unit)));
        });
        this.battleReport = battleReport.toString();
    }

}
