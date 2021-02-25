package pl.daniel.pawlowski.conquerorgame.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Cost {

    private String name;
    private String type;
    private int gold;
    private int wood;
    private int stone;
    private int minutes;

    public Cost costOfLevel(int level, String type, String name){
        return Cost.builder().wood(wood * level).stone(stone * level).gold(gold * level).minutes(minutes * level).type(type).name(name).build();
    }

    public Cost costOfNumber(int number, String name){
        return Cost.builder().wood(wood * number).stone(stone * number).gold(gold * number).minutes(minutes * number).name(name).build();
    }
}
