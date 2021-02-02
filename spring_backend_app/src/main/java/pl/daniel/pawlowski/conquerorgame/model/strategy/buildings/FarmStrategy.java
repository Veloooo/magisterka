package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class FarmStrategy extends BuildingsUpgradeStrategy {
    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getFarm();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setFarm(this.getUser().getBuildings().getFarm() + 1);

    }
}
