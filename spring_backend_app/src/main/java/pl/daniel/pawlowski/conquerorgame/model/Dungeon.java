package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "dungeons")
public class Dungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "reward", referencedColumnName = "id")
    private Item reward;

    private int level;

    private int completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    @JsonIgnore
    private Hero hero;

    @OneToMany(
            mappedBy = "dungeon",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DungeonUnit> dungeonUnits = new ArrayList<>();

    public void addDungeonUnit(DungeonUnit dungeonUnit) {
        dungeonUnits.add(dungeonUnit);
        dungeonUnit.setDungeon(this);
    }

    public void removeDungeonUnit(DungeonUnit dungeonUnit) {
        dungeonUnits.remove(dungeonUnit);
        dungeonUnit.setDungeon(null);
    }
}
