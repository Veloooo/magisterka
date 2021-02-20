package pl.daniel.pawlowski.conquerorgame.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.daniel.pawlowski.conquerorgame.model.Response;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserActionJSON;
import pl.daniel.pawlowski.conquerorgame.repositories.UserRepository;

import static pl.daniel.pawlowski.conquerorgame.utils.Constants.*;

@Service
public class UserService {
    @Value("${auth0.issuer}")
    private String authServerUrl;

    @Autowired
    private UserRepository repository;

    public User getUserInfo(String authorization) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity entity = new HttpEntity(headers);
        return template.exchange(
                authServerUrl + "userinfo", HttpMethod.GET, entity, User.class).getBody();
    }

    public boolean hasAccount(User user) {
        return repository.findAll().stream().anyMatch(t -> user.getId().equals(t.getId()));
    }

    public User getUser(String userId) {
        return repository.findOneById(userId);
    }

    public void addUser(User user) {
        repository.save(user);
    }

    public UserAction getUserAction(String authorization, UserActionJSON userActionJSON) {
        User userAuth = getUserInfo(authorization);
        User userAllInfo = getUser(userAuth.getId());
        return new UserAction(userActionJSON, userAllInfo);
    }

    public Response returnResponse(String actionMessage) {
        Response response = new Response();
        if (OPERATION_SUCCESS_MESSAGE.equals(actionMessage)) {
            response.setStatusCode(200);
        } else if (SERVER_ERROR_MESSAGE.equals(actionMessage)) {
            response.setStatusCode(500);
        } else if (NOT_ENOUGH_RESOURCES_MESSAGE.equals(actionMessage)) {
            response.setStatusCode(432);
        } else if (TOO_MANY_HEROES_MESSAGE.equals(actionMessage)){
            response.setStatusCode(433);
        }
        response.setResponseMessage(actionMessage);
        return response;
    }
}
