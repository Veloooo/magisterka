package pl.daniel.pawlowski.conquerorgame;

import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.daniel.pawlowski.conquerorgame.data.BackgroundService;

import javax.annotation.PostConstruct;

/**
 * Main entry point of application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class App {

    @Autowired
    private JobScheduler jobScheduler;

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

    @PostConstruct
    public void scheduleRecurrently() {
        jobScheduler.scheduleRecurrently(BackgroundService::performScheduledActions, Cron.minutely());
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
