package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

import lombok.extern.slf4j.Slf4j;
import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;

import java.time.LocalDateTime;

@Slf4j
public abstract class BuildingsUpgradeStrategy extends UpgradeStrategy {
    @Override
    public void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime) {
        this.getUser().setBuildingQueue(what);
        this.getUser().setBuildingStartTime(startTime);
        this.getUser().setBuildingFinishTime(finishTime);
        log.info("Building queue set to: " + what);
    }

    @Override
    public void clearQueue() {
        this.getUser().setBuildingQueue(null);
        this.getUser().setBuildingStartTime(null);
        this.getUser().setBuildingFinishTime(null);
        log.info("Building queue cleared for user" + getUser());
    }

    @Override
    public void updateStatistics() {
        this.getUser().getPlayerStatistics().updateBuildings();
    }

    @Override
    public boolean areRequirementsFulfilled() {
        return this.getUser().getBuildings().getHall() > getLevel();
    }
}
