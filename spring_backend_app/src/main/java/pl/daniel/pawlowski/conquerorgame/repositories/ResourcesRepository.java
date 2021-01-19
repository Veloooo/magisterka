package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.Population;
import pl.daniel.pawlowski.conquerorgame.model.Resources;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

}
