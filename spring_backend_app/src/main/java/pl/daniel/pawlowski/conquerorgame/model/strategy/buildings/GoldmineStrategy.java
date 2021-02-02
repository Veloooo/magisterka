package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class GoldmineStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getResources().getGoldmineLvl();
    }

    @Override
    public void upgrade() {
        this.getUser().getResources().setGoldmineLvl(this.getUser().getResources().getGoldmineLvl() + 1);
    }
}
