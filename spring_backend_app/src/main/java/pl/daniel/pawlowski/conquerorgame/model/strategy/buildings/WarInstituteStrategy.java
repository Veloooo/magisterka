package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class WarInstituteStrategy extends BuildingsUpgradeStrategy {
    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getWar();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setWar(this.getUser().getBuildings().getWar() + 1);

    }
}
