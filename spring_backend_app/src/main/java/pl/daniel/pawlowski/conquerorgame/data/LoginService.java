package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

@Service
public class LoginService {


    @Autowired
    private UserRepository repository;

    public boolean hasAccount(User user){
        return repository.findAll().stream().anyMatch(t -> user.getId().equals(t.getId()));
    }

    public void addUser(User user){
        repository.save(user);
    }
}
