package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class HallStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getHall();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setHall(this.getUser().getBuildings().getHall() + 1);
    }
}
