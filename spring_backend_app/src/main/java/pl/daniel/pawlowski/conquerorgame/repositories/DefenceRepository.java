package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.Buildings;
import pl.daniel.pawlowski.conquerorgame.model.Defence;

public interface DefenceRepository extends JpaRepository<Defence, Long> {

}
