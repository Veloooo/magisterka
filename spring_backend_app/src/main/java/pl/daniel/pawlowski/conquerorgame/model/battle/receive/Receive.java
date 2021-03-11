package pl.daniel.pawlowski.conquerorgame.model.battle.receive;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Receive {
    private int newAmount;
    private double additionalHp;

    public Receive(int newAmount, double additionalHp) {
        this.newAmount = newAmount;
        this.additionalHp = additionalHp;
    }
}
