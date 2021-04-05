package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@Entity
@Table(name = "heroes")
public class Hero {

    @OneToMany(
            mappedBy = "hero",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        item.setHero(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setHero(null);
    }



    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "statistics_id", referencedColumnName = "id")
    private Statistics statistics;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "class")
    private String heroClass;

    private int level;

    private int dungeonsCompleted;

    private int exp;

    private String mainStatistic;



    @OneToMany(
            mappedBy = "hero",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Dungeon> dungeons = new ArrayList<>();

    public void addDungeon(Dungeon dungeon) {
        dungeons.add(dungeon);
        dungeon.setHero(this);
    }

    public void removeDungeon(Dungeon dungeon) {
        dungeons.remove(dungeon);
        dungeon.setHero(null);
    }


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hero")
    @JsonIgnore
    private Mission mission;

    public void addExp(int exp){
        this.exp += exp;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", heroClass='" + heroClass + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return id == hero.id &&
                level == hero.level &&
                Objects.equals(heroClass, hero.heroClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heroClass, level);
    }
}
