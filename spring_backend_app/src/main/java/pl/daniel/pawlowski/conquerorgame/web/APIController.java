package pl.daniel.pawlowski.conquerorgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.daniel.pawlowski.conquerorgame.data.UserService;
import pl.daniel.pawlowski.conquerorgame.model.Fraction;
import pl.daniel.pawlowski.conquerorgame.model.Message;
import pl.daniel.pawlowski.conquerorgame.model.User;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        System.out.println("Message called");
        return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/account")
    public String logIn(@RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {
        User userInfo = userService.getUserInfo(authorization);
        boolean hasAccount = userService.hasAccount(userInfo);
        if (hasAccount)
            return mapper.writeValueAsString(userInfo);
        else {
            //return "{\"race\": \"race\", \"nick\":\"nick\"}";
            return "";
        }
    }

    @GetMapping(value = "/account/finalize")
    public Message finalizeRegister(@RequestHeader(value = "Authorization") String authorization, @RequestBody User user) throws JsonProcessingException {
        System.out.println("Finalize");
        User userInfo = userService.getUserInfo(authorization);
        userInfo.setFraction(user.getFraction());
        userService.addUser(userInfo);
        return new Message("User created");
    }


    @GetMapping(value = "/private-scoped")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
