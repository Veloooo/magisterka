package pl.daniel.pawlowski.conquerorgame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.daniel.pawlowski.conquerorgame.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneById(String id);
    List<User> findAllByBuildingQueueNotNullOrResearchQueueNotNull();
}
