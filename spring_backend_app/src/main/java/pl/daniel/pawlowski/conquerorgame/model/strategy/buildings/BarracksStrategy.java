package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class BarracksStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getBarracks();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setBarracks(this.getUser().getBuildings().getBarracks() + 1);
    }
}
