package pl.daniel.pawlowski.conquerorgame.model.battle.receive;

import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

public interface UnitReceiveDamageStrategy {

    void receiveDamage(Unit unit, double damageToReceive);
}
