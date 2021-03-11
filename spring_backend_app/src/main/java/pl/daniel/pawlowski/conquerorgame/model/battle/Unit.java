package pl.daniel.pawlowski.conquerorgame.model.battle;


import lombok.*;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.Attack;
import pl.daniel.pawlowski.conquerorgame.model.battle.attack.UnitAttackStrategy;
import pl.daniel.pawlowski.conquerorgame.model.battle.receive.UnitReceiveDamageStrategy;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class Unit {

    private int level;
    private String armyId;

    private String name;

    private int amount;
    private int healthPoints;
    private int mana;
    private int spellDamage;
    private double healthPointsDamagedUnit;
    private double damageMin;
    private double damageMax;
    private double armour;
    private double speed;

    private UnitAttackStrategy unitAttackStrategy;
    private UnitReceiveDamageStrategy receiveDamageStrategy;

    public Attack attack(){
        return unitAttackStrategy.attackUnit(this);
    }
    public void receiveDamage(double damage){
        receiveDamageStrategy.receiveDamage(this, damage);
    }

    public Unit() {
    }

    public Unit(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public Unit(Unit unit) {
        this.level = unit.level;
        this.name = unit.name;
        this.healthPoints = unit.healthPoints;
        this.damageMax = unit.damageMax;
        this.damageMin = unit.damageMin;
        this.armour = unit.armour;
        this.speed = unit.speed;
        this.unitAttackStrategy = unit.unitAttackStrategy;
        this.receiveDamageStrategy = unit.receiveDamageStrategy;
    }

    public void copyUnitValues(Unit unit){
        this.level = unit.level;
        this.healthPoints = unit.healthPoints;
        this.damageMax = unit.damageMax;
        this.damageMin = unit.damageMin;
        this.armour = unit.armour;
        this.speed = unit.speed;
        this.unitAttackStrategy = unit.unitAttackStrategy;
        this.receiveDamageStrategy = unit.receiveDamageStrategy;
    }
}
