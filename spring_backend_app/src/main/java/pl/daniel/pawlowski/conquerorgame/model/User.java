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
}

