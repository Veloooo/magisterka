package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class MoatStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getDefence().getMoat();
    }

    @Override
    public void upgrade() {
        this.getUser().getDefence().setMoat(this.getUser().getDefence().getMoat()+ 1);
    }
}
