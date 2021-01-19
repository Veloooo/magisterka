package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
public class User {
    @Id
    @JsonProperty("sub")
    private String id;

    private String name;

    private String fraction;

    private Integer points;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn (name="population_id", referencedColumnName = "id")
    private Population population;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn (name="resources_id", referencedColumnName = "id")
    private Resources resources;


}

