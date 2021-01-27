package pl.daniel.pawlowski.conquerorgame.model.useractions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserActionJSON {
    String data;
    int action;
}
