package pl.daniel.pawlowski.conquerorgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private LocalDateTime eventDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
}
