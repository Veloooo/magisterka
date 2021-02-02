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
public class Buildings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int hall;

    private int barracks;

    private int research;

    private int war;

    private int farm;

    private int vault;

    @Column(name = "stone_warehouse")
    private int stoneWarehouse;

    @Column(name = "wood_warehouse")
    private int woodWarehouse;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "buildings")
    @JsonIgnore
    private User user;

}
