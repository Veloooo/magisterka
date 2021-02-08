package pl.daniel.pawlowski.conquerorgame.model.strategy;

import lombok.extern.slf4j.Slf4j;
import pl.daniel.pawlowski.conquerorgame.model.User;

import java.time.LocalDateTime;

@Slf4j
public abstract class UpgradeStrategy {
    private User user;

    public abstract int getLevel();
    public abstract void upgrade();
    public abstract void clearQueue();
    public abstract void setQueue(String what, LocalDateTime startTime, LocalDateTime finishTime);
    public abstract boolean areRequirementsFulfilled();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected void setPeople(){
        int multiplication = 9 + this.getUser().getResearch().getAgriculture();
        log.info("Current multiplication: " + multiplication);
        int currentFree = getUser().getPopulation().getTotal();
        log.info("Current free workers: " + currentFree);
        int currentPeople = getUser().getPeople();
        log.info("Current total people: " + currentPeople);
        this.getUser().setPeople(this.getUser().getBuildings().getFarm() * multiplication);
        log.info("Current people: " + this.getUser().getPeople());
        this.getUser().getPopulation().setTotal(this.getUser().getPeople() - currentPeople + currentFree);
        log.info("Free workers after update: " + this.getUser().getPopulation().getTotal());
    }
}
