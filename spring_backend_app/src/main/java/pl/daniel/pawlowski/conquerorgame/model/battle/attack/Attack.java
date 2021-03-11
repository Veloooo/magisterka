package pl.daniel.pawlowski.conquerorgame.model.battle.attack;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Attack {
    private double damageGiven;
    private String attackType;
}
