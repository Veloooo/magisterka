package pl.daniel.pawlowski.conquerorgame.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public Cost costOfLevel(int level){
        return Cost.builder().wood(wood * level).stone(stone * level).gold(gold * level).minutes(minutes * level).build();
    }
}
