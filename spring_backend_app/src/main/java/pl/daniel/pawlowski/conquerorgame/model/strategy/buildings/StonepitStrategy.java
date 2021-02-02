package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class StonepitStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getResources().getStonepitLvl();
    }

    @Override
    public void upgrade() {
        this.getUser().getResources().setStonepitLvl(this.getUser().getResources().getStonepitLvl() + 1);
    }
}
