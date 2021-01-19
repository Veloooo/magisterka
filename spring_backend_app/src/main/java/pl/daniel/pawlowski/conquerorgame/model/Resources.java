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
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sawmill_lvl")
    private int sawmillLvl;

    @Column(name = "sawmill_workers")
    private int sawmillWorkers;

    @Column(name = "goldmine_lvl")
    private int goldmineLvl;

    @Column(name = "goldmine_workers")
    private int goldmineWorkers;

    @Column(name = "stonepit_lvl")
    private int stonepitLvl;

    @Column(name = "stonepit_workers")
    private int stonepitWorkers;

    @Column(name = "free_workers")
    private int freeWorkers;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "resources")
    @JsonIgnore
    private User user;
}
