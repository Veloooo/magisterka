package pl.daniel.pawlowski.conquerorgame.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.daniel.pawlowski.conquerorgame.model.Mission;
import pl.daniel.pawlowski.conquerorgame.model.MissionJSON;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ObjectMapper.class, MappingService.class})
public class MappingServiceTest {

    @Autowired
    MappingService mappingService;

    @Autowired
    ObjectMapper mapper;

    private MissionJSON missionJSON;
    private Mission mission;

    @PostConstruct
    public void initializeMission() throws JsonProcessingException {
        String data = "{\"hero\":{\"id\":16,\"heroClass\":\"Wizard\",\"level\":1,\"statistics\":{\"id\":16,\"strength\":20,\"intelligence\":15,\"agility\":10,\"charisma\":13,\"skillPoints\":10},\"items\":[]},\"target\":0,\"type\":\"Station\",\"time\":4,\"units\":{\"unit1\":3,\"unit2\":2,\"unit3\":1,\"unit4\":1,\"unit5\":2,\"unit6\":3}}";
        missionJSON = mapper.readValue(data, MissionJSON.class);
    }

    @Test
    public void calculateTimeToDungeons() {
        missionJSON.setTarget(0);
        mission = mappingService.mapMissionJsonToDTO(missionJSON, 1);
        compareTimes(30);
    }

    @Test
    public void calculateTimeFromPosition1To2(){
        missionJSON.setTarget(2);
        mission = mappingService.mapMissionJsonToDTO(missionJSON, 1);
        compareTimes(30);
    }

    @Test
    public void calculateTimeFromPosition1To3() {
        missionJSON.setTarget(3);
        mission = mappingService.mapMissionJsonToDTO(missionJSON, 1);
        compareTimes(60);
    }

    @Test
    public void getMissionFinishTimeEqual() {
        missionJSON.setTime(0);
        mission = mappingService.mapMissionJsonToDTO(missionJSON, 1);
        Assert.assertEquals(mission.getMissionArrivalTime(), mission.getMissionFinishTime());
    }

    @Test
    public void getMissionFinishTimeDifferenceOneHour() {
        missionJSON.setTime(1);
        mission = mappingService.mapMissionJsonToDTO(missionJSON, 1);
        Assert.assertEquals(mission.getMissionArrivalTime().plusHours(1), mission.getMissionFinishTime());
    }

    private void compareTimes(int minutes) {
        Assert.assertEquals(LocalDateTime.now().plusMinutes(minutes).truncatedTo(ChronoUnit.SECONDS), mission.getMissionArrivalTime().truncatedTo(ChronoUnit.SECONDS));
    }

}
