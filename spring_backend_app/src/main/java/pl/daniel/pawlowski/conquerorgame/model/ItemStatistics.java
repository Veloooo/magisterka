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
@Table(name = "item_statistics")
public class ItemStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int strength;

    private int intelligence;

    private int agility;

    private int charisma;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "statistics")
    @JsonIgnore
    private Item item;

    @Override
    public String toString() {
        return "ItemStatistics{" +
                "id=" + id +
                ", strength=" + strength +
                ", intelligence=" + intelligence +
                ", agility=" + agility +
                ", charisma=" + charisma +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStatistics that = (ItemStatistics) o;
        return id == that.id &&
                strength == that.strength &&
                intelligence == that.intelligence &&
                agility == that.agility &&
                charisma == that.charisma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strength, intelligence, agility, charisma);
    }
}
