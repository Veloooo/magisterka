package pl.daniel.pawlowski.conquerorgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.daniel.pawlowski.conquerorgame.data.GameService;
import pl.daniel.pawlowski.conquerorgame.data.UserService;
import pl.daniel.pawlowski.conquerorgame.model.Response;
import pl.daniel.pawlowski.conquerorgame.model.User;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserActionJSON;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    ObjectMapper mapper = new ObjectMapper();
    private final String OPERATION_SUCCESS_MESSAGE = "Ok";

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

    @PostMapping(value = "/population")
    public String populationAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws JsonProcessingException {
        UserAction action = userService.getUserAction(authorization,userActionJSON);
        String actionMessage = gameService.populationAction(action);
        return mapper.writeValueAsString(userService.returnResponse(actionMessage));
    }

    @PostMapping(value = "/resources")
    public String resourcesAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws IOException {
        UserAction action = userService.getUserAction(authorization,userActionJSON);
        String actionMessage = gameService.resourcesAction(action);
        return mapper.writeValueAsString(userService.returnResponse(actionMessage));
    }

    @PostMapping(value = "/research")
    public String researchAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws IOException {
        UserAction action = userService.getUserAction(authorization,userActionJSON);
        String actionMessage = gameService.upgradeAction(action);
        return mapper.writeValueAsString(userService.returnResponse(actionMessage));
    }

    @PostMapping(value = "/buildings")
    public String buildingsAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws IOException {
        UserAction action = userService.getUserAction(authorization,userActionJSON);
        String actionMessage = gameService.upgradeAction(action);
        return mapper.writeValueAsString(userService.returnResponse(actionMessage));
    }
    @PostMapping(value = "/tavern")
    public String tavernAction(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserActionJSON userActionJSON) throws IOException {
        UserAction action = userService.getUserAction(authorization,userActionJSON);
        String actionMessage = gameService.heroAction(action);
        return mapper.writeValueAsString(userService.returnResponse(actionMessage));
    }
}
