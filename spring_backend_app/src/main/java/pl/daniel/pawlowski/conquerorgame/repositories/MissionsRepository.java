package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.Buildings;
import pl.daniel.pawlowski.conquerorgame.model.Units;

public interface UnitsRepository extends JpaRepository<Units, Long> {

}
