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
@Table(name = "players_statistics")
public class PlayerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int overall;

    private int buildings;

    private int research;

    private int units;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "playerStatistics")
    @JsonIgnore
    private User user;

    public void updateBuildings() {
        this.buildings++;
        updateOverall(1);

    }

    public void updateResearch() {
        this.research++;
        updateOverall(1);
    }

    private void updateOverall(int update) {
        this.overall += update;
    }

}
