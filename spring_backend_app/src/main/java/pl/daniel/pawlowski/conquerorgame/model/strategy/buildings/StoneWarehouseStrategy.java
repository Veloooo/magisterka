package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class StoneWarehouseStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getStoneWarehouse();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setStoneWarehouse(this.getUser().getBuildings().getStoneWarehouse() + 1);

    }
}
