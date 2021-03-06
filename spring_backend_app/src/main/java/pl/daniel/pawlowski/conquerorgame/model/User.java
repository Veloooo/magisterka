package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Slf4j
public class User {
    @Id
    @JsonProperty("sub")
    private String id;

    private String name;

    private String nick;

    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_position")
    private Integer cityPosition;

    private String fraction;

    private Integer gold;

    private Integer wood;

    private Integer stone;

    private Integer people;

    private String buildingQueue;

    private LocalDateTime buildingFinishTime;

    private LocalDateTime buildingStartTime;

    private String researchQueue;

    private LocalDateTime researchFinishTime;

    private LocalDateTime researchStartTime;

    private int stoneProduction;

    private int woodProduction;

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
    private List<Mission> missions = new ArrayList<>();

    public void addMission(Mission mission) {
        missions.add(mission);
        mission.setUser(this);
    }

    public void removeMission(Mission mission) {
        missions.remove(mission);
        mission.setUser(null);
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
                ", cityPosition='" + cityPosition + '\'' +
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

