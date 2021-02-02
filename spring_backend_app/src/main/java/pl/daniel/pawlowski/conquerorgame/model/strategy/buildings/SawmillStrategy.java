package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class SawmillStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getResources().getSawmillLvl();
    }

    @Override
    public void upgrade() {
        this.getUser().getResources().setSawmillLvl(this.getUser().getResources().getSawmillLvl() + 1);
    }
}
