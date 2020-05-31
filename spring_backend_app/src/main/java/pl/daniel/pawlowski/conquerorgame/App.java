package pl.daniel.pawlowski.conquerorgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main entry point of application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
