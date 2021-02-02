package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class AgricultureStrategy extends ResearchUpgradeStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getAgriculture();
    }

    public void upgrade() {
        this.getUser().getResearch().setAgriculture(this.getUser().getResearch().getAgriculture() + 1);
    }
}
