package pl.daniel.pawlowski.conquerorgame.model.battle;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.UnitAttackStrategy;
import pl.daniel.pawlowski.conquerorgame.model.battle.receive.UnitReceiveDamageStrategy;

@Getter
@Setter
@Data
@Builder
public class Unit {

    private String name;
    private int amount;
    private int healthPoints;
    private double healthPointsDamagedUnit;
    private double damageMin;
    private double damageMax;
    private double armour;
    private double speed;

    private UnitAttackStrategy unitAttackStrategy;
    private UnitReceiveDamageStrategy receiveDamageStrategy;

    public void attack(){

    }
    public void receiveDamage(){

    }


    public Unit(){

    }
}
