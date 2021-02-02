package pl.daniel.pawlowski.conquerorgame.model.strategy.buildings;

public class ResearchInstituteStrategy extends BuildingsUpgradeStrategy {

    @Override
    public int getLevel() {
        return this.getUser().getBuildings().getResearch();
    }

    @Override
    public void upgrade() {
        this.getUser().getBuildings().setResearch(this.getUser().getBuildings().getResearch() + 1);

    }
}
