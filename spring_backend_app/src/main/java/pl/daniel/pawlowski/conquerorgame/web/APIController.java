package pl.daniel.pawlowski.conquerorgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.daniel.pawlowski.conquerorgame.data.GameService;
import pl.daniel.pawlowski.conquerorgame.data.UserService;
import pl.daniel.pawlowski.conquerorgame.model.*;
import pl.daniel.pawlowski.conquerorgame.model.useractions.UserAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

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
    @GetMapping(value = "/account/accountInfo")
    public String getAccountInfo(@RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        User userInfo = userService.getUserInfo(authorization);
        User userAllInfo = gameService.getUser(userInfo.getId());
        return mapper.writeValueAsString(userAllInfo);
    }

    @GetMapping(value = "/account/userAction")
    public String getUserAction(@RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {
        System.out.println("Xsaxsaxasxasxas");
        User userAuth = userService.getUserInfo(authorization);
        User userAllInfo = gameService.getUser(userAuth.getId());
        //userAction.setUser(userAllInfo);
        //gameService.performAction(userAction);
        return "sd";
    }

    @PostMapping(value = "/account/finalize")
    public String finalizeRegister(@RequestHeader(value = "Authorization") String authorization, @RequestBody User user) throws JsonProcessingException {
        System.out.println("Finalize");
        User userInfo = userService.getUserInfo(authorization);
        userInfo.setFraction(user.getFraction());
        userInfo.setCityName("City");
        userInfo.setCityCoordinates("1:1:1");
        Random rand = new Random();

        Population population = new Population();
        population.setUser(userInfo);
        population.setTotal(rand.nextInt(50));
        userInfo.setPopulation(population);

        Resources resources = new Resources();
        resources.setUser(userInfo);
        userInfo.setResources(resources);

        Buildings buildings = new Buildings();
        buildings.setUser(userInfo);
        userInfo.setBuildings(buildings);

        Research research = new Research();
        research.setUser(userInfo);
        userInfo.setResearch(research);

        Units units= new Units();
        units.setUser(userInfo);
        userInfo.setUnits(units);

        Defence defence= new Defence();
        defence.setUser(userInfo);
        userInfo.setDefence(defence);

        Event event = new Event();
        event.setEventDate(LocalDateTime.now().plusHours(30));
        event.setContent("Ostry wpierdol");
        userInfo.addEvent(event);
        userService.addUser(userInfo);
        return mapper.writeValueAsString(userInfo);
    }


    @GetMapping(value = "/private-scoped")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
