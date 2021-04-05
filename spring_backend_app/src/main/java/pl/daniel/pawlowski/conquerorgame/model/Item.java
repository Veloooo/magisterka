package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Data
@Getter
@Setter
@Entity
@Table (name = "items")
public class Item {
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="hero_id")
    @JsonIgnore
    private Hero hero;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String part;

    @Column(name = "is_worn")
    private int isWorn;

    @Column(name = "level_required")
    private int levelRequired;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "statistics_id", referencedColumnName = "id")
    private ItemStatistics statistics;


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", part='" + part + '\'' +
                ", isWorn=" + isWorn +
                ", levelRequired=" + levelRequired +
                '}';
    }
    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reward")
    @JsonIgnore
    private Dungeon dungeon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                isWorn == item.isWorn &&
                levelRequired == item.levelRequired &&
                Objects.equals(name, item.name) &&
                Objects.equals(part, item.part);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, part, isWorn, levelRequired);
    }
}
