package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.Mission;
import pl.daniel.pawlowski.conquerorgame.model.Units;

import java.time.LocalDateTime;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByCalculations(int number);
    List<Mission> findByTargetEqualsAndMissionArrivalTimeAfterAndMissionFinishTimeBeforeAndTypeEquals(int target, LocalDateTime dateTime, LocalDateTime dateTime2, String type);
}
