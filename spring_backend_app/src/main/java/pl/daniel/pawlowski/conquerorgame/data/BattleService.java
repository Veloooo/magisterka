package pl.daniel.pawlowski.conquerorgame.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.battle.BattleResult;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.Attack;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.DefaultAttackStrategy;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.RangedAttackStrategy;

import java.util.*;
import java.util.stream.Collectors;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.ATTACKING_ARMY_INDICATOR;
import static pl.daniel.pawlowski.conquerorgame.utils.Constants.DEFENDING_ARMY_INDICATOR;

@Service
@Slf4j
public class BattleService {

    @Autowired
    ObjectMapper mapper;

    public BattleResult battle(List<Unit> attackingArmy, List<List<Unit>> defendingArmies) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        BattleResult result = new BattleResult();
        setBattleBeginArmy(attackingArmy, defendingArmies, result);

        List<Unit> allUnits = new ArrayList<>(attackingArmy);
        defendingArmies.forEach(allUnits::addAll);

        List<Unit> defendingArmiesTogether = new ArrayList<>();
        defendingArmies.forEach(defendingArmiesTogether::addAll);

        sortUnits(allUnits);
        battleTillEnd(result, allUnits, attackingArmy, defendingArmiesTogether);
        result.calculateLoss();

        return result;
    }

    private void setBattleBeginArmy(List<Unit> attackingArmy, List<List<Unit>> defendingArmy, BattleResult result) {
        HashMap<String, Integer> attackingArmyBegginingAmount = new HashMap<>();
        attackingArmy.forEach(
                unit -> attackingArmyBegginingAmount.put(unit.getName(), unit.getAmount()));
        result.setAttackingArmyBeginning(attackingArmyBegginingAmount);
        result.setAttackingArmyId(attackingArmy.get(0).getArmyId());

        HashMap<String, HashMap<String, Integer>> defendingArmyBegginingAmount = new HashMap<>();
        defendingArmy.forEach(army -> {
            HashMap<String, Integer> defendingArmyBegginingAmountMap = new HashMap<>();
            army.forEach(unit -> defendingArmyBegginingAmountMap.put(unit.getName(), unit.getAmount()));
            defendingArmyBegginingAmount.put(army.get(0).getArmyId(), defendingArmyBegginingAmountMap);
        });
        result.setDefendingArmyBeginning(defendingArmyBegginingAmount);
    }

    private void sortUnits(List<Unit> allUnits) {
        allUnits.sort(new Comparator<Unit>() {
            @Override
            public int compare(Unit u1, Unit u2) {
                int unit1Index = getOrderIndex(u1);
                int unit2Index = getOrderIndex(u2);
                if (unit1Index == unit2Index)
                    return (int) (u2.getSpeed() - u1.getSpeed());
                else
                    return unit1Index - unit2Index;
            }

            private int getOrderIndex(Unit unit) {
                if (unit == null)
                    return 0;
                else if (unit.getUnitAttackStrategy() instanceof RangedAttackStrategy)
                    return 1;
                else if (unit.getUnitAttackStrategy() instanceof DefaultAttackStrategy)
                    return 2;
                else throw new RuntimeException("Unexpected Attack strategy");
            }
        });
    }

    private BattleResult battleTillEnd(BattleResult result, List<Unit> allUnits, List<Unit> attackingArmy, List<Unit> defendingArmy) {
        int indexAttacking = 0;
        boolean battleOnGoing = true;
        while (battleOnGoing) {
            Unit attackingUnit = allUnits.get(indexAttacking);
            String side = getSideOfArmy(attackingUnit.getArmyId(), attackingArmy);
            Unit attackedUnit;
            if (ATTACKING_ARMY_INDICATOR.equals(side)) {
                attackedUnit = attackingUnit.getUnitAttackStrategy().getAttackedUnit(defendingArmy);
            } else {
                attackedUnit = attackingUnit.getUnitAttackStrategy().getAttackedUnit(attackingArmy);
            }
            attack(attackingUnit, attackedUnit);
            if (ATTACKING_ARMY_INDICATOR.equals(side)) {
                attackingArmy = updateUnitList(attackingArmy, attackingUnit);
                defendingArmy = updateUnitList(defendingArmy, attackedUnit);
            } else {
                defendingArmy = updateUnitList(defendingArmy, attackingUnit);
                attackingArmy = updateUnitList(attackingArmy, attackedUnit);
            }

            battleOnGoing = defendingArmy.size() > 0 && attackingArmy.size() > 0;
            indexAttacking++;
            if (indexAttacking == allUnits.size())
                indexAttacking = 0;
        }


        attackingArmy.forEach(unit -> result.getAttackingArmyEnd().put(unit.getName(), unit.getAmount()));
        defendingArmy.forEach(
                unit -> {
                    if(result.getDefendingArmyEnd().keySet().contains(unit.getArmyId())) {
                        result.getDefendingArmyEnd().get(unit.getArmyId()).put(unit.getName(), unit.getAmount());
                    }
                    else{
                        result.getDefendingArmyEnd().put(unit.getArmyId(),new HashMap<>());
                    }
                }
        );

        return result;

    }

    private void attack(Unit unit, Unit defending) {
        log.info(unit.getName() + " will attack " + defending.getName());
        Attack attack = unit.attack();
        defending.receiveDamage(attack.getDamageGiven());
    }

    private String getSideOfArmy(String armyId, List<Unit> attackingArmy) {
        if (attackingArmy.stream().anyMatch(armyIdStream -> armyId.equals(armyIdStream.getArmyId())))
            return ATTACKING_ARMY_INDICATOR;
        else
            return DEFENDING_ARMY_INDICATOR;
    }

    private List<Unit> updateUnitList(List<Unit> units, Unit unit) {
        return updateUnitList(units, unit.getName(), unit.getAmount(), unit.getArmyId()).stream()
                .filter(unit1 -> unit1.getAmount() > 0)
                .collect(Collectors.toList());
    }

    private List<Unit> updateUnitList(List<Unit> units, final String name, int newAmount, String armyId) {
        return units.stream()
                .map(unit -> updateUnit(unit, newAmount, name, armyId))
                .collect(Collectors.toList());
    }

    private Unit updateUnit(Unit unit, int amount, String name, String armyId) {
        if (name.equals(unit.getName()) && armyId.equals(unit.getArmyId()))
            unit.setAmount(amount);
        return unit;
    }
}
