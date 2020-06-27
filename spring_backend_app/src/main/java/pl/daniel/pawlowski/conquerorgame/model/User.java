package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Getter
@Setter
@Entity
public class User {
    @Id
    @JsonProperty("sub")
    private String id;

    private String name;

    private Fraction fraction;

    private Integer points;
}

