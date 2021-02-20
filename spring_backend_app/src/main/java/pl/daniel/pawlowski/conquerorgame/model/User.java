package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User {
    @Id
    @JsonProperty("sub")
    private String id;

    private String name;

    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_coordinates")
    private String cityCoordinates;

    private String fraction;

    private Integer gold;

    private Integer wood;

    private Integer stone;

    private Integer people;

    @Column(name = "building_queue")
    private String buildingQueue;

    @Column(name = "building_finish_time")
    private LocalDateTime buildingFinishTime;

    @Column(name = "building_start_time")
    private LocalDateTime buildingStartTime;

    @Column(name = "research_queue")
    private String researchQueue;

    @Column(name = "research_finish_time")
    private LocalDateTime researchFinishTime;

    @Column(name = "research_start_time")
    private LocalDateTime researchStartTime;

    @Column(name = "stone_production")
    private int stoneProduction;

    @Column(name = "wood_production")
    private int woodProduction;

    @Column(name = "gold_production")
    private int goldProduction;

    private Integer points;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "population_id", referencedColumnName = "id")
    private Population population;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "resources_id", referencedColumnName = "id")
    private Resources resources;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "buildings_id", referencedColumnName = "id")
    private Buildings buildings;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "research_id", referencedColumnName = "id")
    private Research research;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "units_id", referencedColumnName = "id")
    private Units units;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "defence_id", referencedColumnName = "id")
    private Defence defence;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "statistics_id", referencedColumnName = "id")
    private PlayerStatistics playerStatistics;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
        event.setUser(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setUser(null);
    }

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Hero> heroes = new ArrayList<>();

    public void addHero(Hero hero) {
        heroes.add(hero);
        hero.setUser(this);
    }

    public void removeHero(Hero hero) {
        events.remove(hero);
        hero.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCoordinates='" + cityCoordinates + '\'' +
                ", fraction='" + fraction + '\'' +
                ", gold=" + gold +
                ", wood=" + wood +
                ", stone=" + stone +
                ", people=" + people +
                ", buildingQueue='" + buildingQueue + '\'' +
                ", buildingFinishTime=" + buildingFinishTime +
                ", buildingStartTime=" + buildingStartTime +
                ", researchQueue='" + researchQueue + '\'' +
                ", researchFinishTime=" + researchFinishTime +
                ", researchStartTime=" + researchStartTime +
                ", stoneProduction=" + stoneProduction +
                ", woodProduction=" + woodProduction +
                ", goldProduction=" + goldProduction +
                ", points=" + points +
                '}';
    }
}

