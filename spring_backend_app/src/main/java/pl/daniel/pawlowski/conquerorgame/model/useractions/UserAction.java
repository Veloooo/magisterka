package pl.daniel.pawlowski.conquerorgame.model.useractions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.daniel.pawlowski.conquerorgame.model.User;

@Data
@Getter
@Setter
public class UserAction extends UserActionJSON {
    User user;
}
