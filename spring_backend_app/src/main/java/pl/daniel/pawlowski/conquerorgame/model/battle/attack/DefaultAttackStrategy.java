package pl.daniel.pawlowski.conquerorgame.model.battle.attack;

import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

public class DefaultAttackStrategy implements UnitAttackStrategy {
    private static DefaultAttackStrategy instance;

    @Override
    public UnitAttackStrategy getInstance() {
        if(instance == null)
            instance = new DefaultAttackStrategy();
        return instance;
    }

    private DefaultAttackStrategy(){
    }

    @Override
    public void attack(Unit unit) {
        double damageGiven = unit.getAmount() * ((Math.random() * (unit.getDamageMax() - unit.getDamageMin())) + unit.getDamageMin());
    }
}
