package pl.daniel.pawlowski.conquerorgame.model.battle.attack;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

@Value
@Slf4j
public class RangedAttackStrategy implements UnitAttackStrategy {

    @Override
    public Attack attackUnit(Unit unit) {
        Attack attack = new Attack();
        attack.setDamageGiven(unit.getAmount() * ((Math.random() * (unit.getDamageMax() - unit.getDamageMin())) + unit.getDamageMin()));
        attack.setAttackType("RANGED");
        log.info(unit.getName() + " with amount of " + unit.getAmount() + " does attack of damage: " + attack.getDamageGiven());
        return attack;
    }
}
