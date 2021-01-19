package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;


    public User getUser(String userId){
        return userRepository.findOneById(userId);
    }

}
