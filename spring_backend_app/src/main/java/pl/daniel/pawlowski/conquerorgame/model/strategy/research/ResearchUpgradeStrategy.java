package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;

import java.time.LocalDateTime;

public abstract class ResearchUpgradeStrategy extends UpgradeStrategy {
    @Override
    public void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime) {
        this.getUser().setResearchQueue(what);
        this.getUser().setResearchStartTime(startTime);
        this.getUser().setResearchFinishTime(finishTime);
    }

    @Override
    public void clearQueue() {
        this.getUser().setResearchQueue(null);
        this.getUser().setResearchStartTime(null);
        this.getUser().setResearchFinishTime(null);
    }
}
