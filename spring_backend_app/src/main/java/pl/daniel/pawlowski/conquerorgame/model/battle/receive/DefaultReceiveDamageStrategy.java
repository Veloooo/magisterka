package pl.daniel.pawlowski.conquerorgame.model.battle.receive;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

@Slf4j
@Value
public class DefaultReceiveDamageStrategy implements UnitReceiveDamageStrategy {

    @Override
    public void receiveDamage(Unit unit, double damageToReceive) {
        double damageReceived = damageToReceive * (20 / (20 + unit.getArmour()));
        double overallHp = unit.getAmount() * unit.getHealthPoints();
        overallHp -= damageReceived;
        int newAmount;
        double additionalHp;
        if(overallHp < 0) {
            newAmount = 0;
            additionalHp = 0;
        }
        else {
            newAmount = (int) Math.ceil(overallHp / unit.getHealthPoints());
            additionalHp = overallHp % unit.getHealthPoints();
        }
        unit.setAmount(newAmount);
        unit.setHealthPointsDamagedUnit(additionalHp);
        log.info(unit.getName() + " receives " + damageReceived + " damage. New overall hp = " + overallHp + ", new amount = " + newAmount + " and last unit is " + additionalHp + " hp");
    }
}
