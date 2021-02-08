package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class GuardTowersStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getDefence().getGuardTowers();
    }

    @Override
    public void upgrade() {
        this.getUser().getDefence().setGuardTowers(this.getUser().getDefence().getGuardTowers()+ 1);
    }
}
