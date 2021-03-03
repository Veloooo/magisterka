package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.Mission;
import pl.daniel.pawlowski.conquerorgame.model.Units;

import java.util.List;

public interface MissionsRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByCalculations(int number);
}
