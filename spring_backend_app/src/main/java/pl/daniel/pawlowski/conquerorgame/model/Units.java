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
public class Units {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_1")
    private int unit1;

    @Column(name = "unit_2")
    private int unit2;

    @Column(name = "unit_3")
    private int unit3;

    @Column(name = "unit_4")
    private int unit4;

    @Column(name = "unit_5")
    private int unit5;

    @Column(name = "unit_6")
    private int unit6;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "units")
    @JsonIgnore
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "units")
    @JsonIgnore
    private Mission mission;




}
