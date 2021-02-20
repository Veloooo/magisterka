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
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int strength;

    private int intelligence;

    private int agility;

    private int charisma;

    @Column(name="skill_points")
    private int skillPoints;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "statistics")
    @JsonIgnore
    private Hero hero;

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", strength=" + strength +
                ", intelligence=" + intelligence +
                ", agility=" + agility +
                ", charisma=" + charisma +
                ", skillPoints=" + skillPoints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return id == that.id &&
                strength == that.strength &&
                intelligence == that.intelligence &&
                agility == that.agility &&
                charisma == that.charisma &&
                skillPoints == that.skillPoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strength, intelligence, agility, charisma, skillPoints);
    }
}
