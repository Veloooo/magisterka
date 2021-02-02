package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

import pl.daniel.pawlowski.conquerorgame.model.strategy.UpgradeStrategy;

import java.time.LocalDateTime;

public abstract class BuildingsUpgradeStrategy extends UpgradeStrategy {
    @Override
    public void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime) {
        this.getUser().setBuildingQueue(what);
        this.getUser().setBuildingStartTime(startTime);
        this.getUser().setBuildingFinishTime(finishTime);
    }

    @Override
    public void clearQueue() {
        this.getUser().setBuildingQueue(null);
        this.getUser().setBuildingStartTime(null);
        this.getUser().setBuildingFinishTime(null);
    }
}
