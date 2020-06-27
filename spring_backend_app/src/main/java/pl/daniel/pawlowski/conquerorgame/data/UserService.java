package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

@Service
public class UserService {
    @Value("${auth0.issuer}")
    private String authServerUrl;

    @Autowired
    private UserRepository repository;

    public User getUserInfo(String authorization){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity entity = new HttpEntity(headers);
        return template.exchange(
                authServerUrl + "userinfo", HttpMethod.GET, entity, User.class).getBody();
    }

    public boolean hasAccount(User user){
        return repository.findAll().stream().anyMatch(t -> user.getId().equals(t.getId()));
    }

    public void addUser(User user){
        repository.save(user);
    }
}
