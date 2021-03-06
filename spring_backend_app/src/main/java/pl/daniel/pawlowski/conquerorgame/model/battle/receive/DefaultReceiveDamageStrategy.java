package pl.daniel.pawlowski.conquerorgame.model.battle.receive;

import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

public class DefaultReceiveDamageStrategy implements UnitReceiveDamageStrategy {

    @Override
    public void receiveDamage(Unit unit, double damageToReceive) {
        double damageReceived = damageToReceive * (20 / (20 + unit.getArmour()));
        double overallHp = unit.getAmount() * unit.getHealthPoints();
        overallHp -= damageReceived;
        int newAmount = (int)(overallHp / unit.getHealthPoints());
        double additionalHp = overallHp % unit.getHealthPoints();
        unit.setAmount(newAmount);
        unit.setHealthPointsDamagedUnit(additionalHp);
    }
}
