package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class WallStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getDefence().getCityWall();
    }

    @Override
    public void upgrade() {
        this.getUser().getDefence().setCityWall(this.getUser().getDefence().getCityWall()+ 1);
    }
}
