package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class VaultStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getVault();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setVault(this.getUser().getBuildings().getVault() + 1);

    }
}
