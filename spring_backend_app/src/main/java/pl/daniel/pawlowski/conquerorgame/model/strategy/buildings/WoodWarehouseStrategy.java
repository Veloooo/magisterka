package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class WoodWarehouseStrategy extends BuildingsUpgradeStrategy {
    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getWoodWarehouse();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setWoodWarehouse(this.getUser().getBuildings().getWoodWarehouse() + 1);

    }
}
