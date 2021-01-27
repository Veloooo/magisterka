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
public class Defence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="city_wall")
    private int cityWall;

    @Column(name = "guard_towers")
    private int guardTowers;

    private int moat;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "defence")
    @JsonIgnore
    private User user;

}
