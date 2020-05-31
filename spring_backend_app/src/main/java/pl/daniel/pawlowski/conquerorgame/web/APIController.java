package pl.daniel.pawlowski.conquerorgame.web;

import com.nimbusds.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.daniel.pawlowski.conquerorgame.data.LoginService;
import pl.daniel.pawlowski.conquerorgame.model.Message;
import pl.daniel.pawlowski.conquerorgame.model.User;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

    @Autowired
    LoginService loginService;

    @Value("${auth0.issuer}")
    private String authServerUrl;

    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        System.out.println("Message called");
        return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public Message privateEndpoint(@RequestHeader(value = "Authorization") String authorization) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity entity = new HttpEntity(headers);
        User user = template.exchange(
                authServerUrl + "userinfo", HttpMethod.GET, entity, User.class).getBody();

        if(loginService.hasAccount(user))
            return new Message(user.getName());
        else{
            loginService.addUser(user);
            return new Message("User added!");
        }
    }

    @GetMapping(value = "/private-scoped")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
