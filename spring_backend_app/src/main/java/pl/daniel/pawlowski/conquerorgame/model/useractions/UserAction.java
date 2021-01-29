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

    public UserAction(UserActionJSON userActionJSON, User user){
        this.action = userActionJSON.getAction();
        this.data = userActionJSON.getData();
        this.user = user;
    }
}
