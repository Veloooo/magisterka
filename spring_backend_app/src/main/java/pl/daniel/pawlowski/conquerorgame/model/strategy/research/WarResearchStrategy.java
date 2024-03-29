package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public abstract class WarResearchStrategy extends ResearchUpgradeStrategy {

    @Override
    public boolean areRequirementsFulfilled() {
        return this.getUser().getBuildings().getWar() > getLevel();
    }
}
