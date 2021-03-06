package pl.daniel.pawlowski.conquerorgame.model.battle.attack;

import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

public interface UnitAttackStrategy {
    void attack(Unit unit);
    UnitAttackStrategy getInstance();
}
