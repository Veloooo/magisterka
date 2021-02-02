package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class BuildingStrategy extends ResearchUpgradeStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getBuilding();
    }

    public void upgrade() {
        this.getUser().getResearch().setBuilding(this.getUser().getResearch().getBuilding() + 1);
    }
}
