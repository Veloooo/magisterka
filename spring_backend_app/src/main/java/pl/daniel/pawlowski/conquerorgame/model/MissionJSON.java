package pl.daniel.pawlowski.conquerorgame.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MissionJSON {
    private Units units;

    private Hero hero;

    private String type;

    private int time;

    private int target;


}
