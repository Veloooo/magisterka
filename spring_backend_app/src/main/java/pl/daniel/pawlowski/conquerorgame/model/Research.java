package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Research {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int building;

    private int agriculture;

    private int logistics;

    private int attack;

    private int armour;

    private int mining;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "research")
    @JsonIgnore
    private User user;

    public Research() {
        this.building = 1;
        this.agriculture = 1;
        this.logistics = 1;
        this.attack = 1;
        this.armour = 1;
        this.mining = 1;

    }
}
