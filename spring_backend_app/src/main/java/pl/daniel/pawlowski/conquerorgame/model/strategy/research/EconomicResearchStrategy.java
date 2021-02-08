package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public abstract class EconomicResearchStrategy extends ResearchUpgradeStrategy {

    @Override
    public boolean areRequirementsFulfilled() {
        return this.getUser().getBuildings().getResearch() > getLevel();
    }
}
