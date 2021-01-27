package pl.daniel.pawlowski.conquerorgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.daniel.pawlowski.conquerorgame.data.GameService;
import pl.daniel.pawlowski.conquerorgame.data.UserService;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserActionJSON;

@RestController
@RequestMapping(path = "api/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @GetMapping(value = "/population")
    public String getPopulation(@RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {
        User userInfo = userService.getUserInfo(authorization);
        boolean hasAccount = userService.hasAccount(userInfo);
        if (hasAccount)
            return mapper.writeValueAsString(userInfo);
        else {
            //return "{\"race\": \"race\", \"nick\":\"nick\"}";
            return "";
        }
    }

    @PostMapping(value = "/userAction")
    public String getUserAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws JsonProcessingException {
        System.out.println("Xsaxsaxasxasxas");
        User userAuth = userService.getUserInfo(authorization);
        User userAllInfo = gameService.getUser(userAuth.getId());
        //userAction.setUser(userAllInfo);
        //gameService.performAction(userAction);
        return "sd";
    }
}
