package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
public class DungeonUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dungeon_id")
    @JsonIgnore
    private Dungeon dungeon;

    public DungeonUnit() {
    }

    public DungeonUnit(String name, int amount){
        this.name = name;
        this.amount = amount;
    }
}
