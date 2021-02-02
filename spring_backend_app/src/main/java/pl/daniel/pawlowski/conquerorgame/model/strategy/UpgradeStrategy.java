package pl.daniel.pawlowski.conquerorgame.model.strategy;

import pl.daniel.pawlowski.conquerorgame.model.User;

import java.time.LocalDateTime;

public abstract class UpgradeStrategy {
    private User user;

    public abstract int getLevel();
    public abstract void upgrade();
    public abstract void clearQueue();
    public abstract void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
