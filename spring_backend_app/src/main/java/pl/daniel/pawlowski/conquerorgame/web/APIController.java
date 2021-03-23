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
    ObjectMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

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

    @GetMapping(value = "/account/allUsers")
    public String getAllUsersCityInfo() throws JsonProcessingException {
        List<User> allUsers = gameService.getAllUsersCityInfo();
        return mapper.writeValueAsString(allUsers);
    }

    @PostMapping(value = "/account/finalize")
    public String finalizeRegister(@RequestHeader(value = "Authorization") String authorization, @RequestBody User user) throws JsonProcessingException {
        System.out.println("Finalize");
        User userInfo = userService.getUserInfo(authorization);
        userInfo.setFraction(user.getFraction());
        userInfo.setCityName("City");
        userInfo.setCityPosition(1);
        userInfo.setGold(200);
        userInfo.setWood(100);
        userInfo.setStone(100);
        userInfo.setPeople(10);

        Population population = new Population();
        population.setTotal(10);
        population.setUser(userInfo);
        userInfo.setPopulation(population);

        Resources resources = new Resources();
        resources.setGoldmineLvl(1);
        resources.setSawmillLvl(1);
        resources.setStonepitLvl(1);
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

        PlayerStatistics playerStatistics= new PlayerStatistics();
        playerStatistics.setUser(userInfo);
        userInfo.setPlayerStatistics(playerStatistics);

        userService.addUser(userInfo);
        return mapper.writeValueAsString(userInfo);
    }
}
