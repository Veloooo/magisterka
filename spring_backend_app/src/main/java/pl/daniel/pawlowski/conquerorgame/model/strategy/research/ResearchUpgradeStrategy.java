package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

import lombok.extern.slf4j.Slf4j;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;

import java.time.LocalDateTime;

@Slf4j
public abstract class ResearchUpgradeStrategy extends UpgradeStrategy {
    @Override
    public void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime) {
        this.getUser().setResearchQueue(what);
        this.getUser().setResearchStartTime(startTime);
        this.getUser().setResearchFinishTime(finishTime);
        log.info("ResearchQueue set to: " + what );
    }

    @Override
    public void clearQueue() {
        this.getUser().setResearchQueue(null);
        this.getUser().setResearchStartTime(null);
        this.getUser().setResearchFinishTime(null);
        log.info("ResearchQueue cleared");
    }

    @Override
    public void updateStatistics() {
        this.getUser().getPlayerStatistics().updateResearch();
    }
}
