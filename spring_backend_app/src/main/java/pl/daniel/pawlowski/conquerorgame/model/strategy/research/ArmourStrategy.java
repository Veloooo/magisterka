package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class ArmourStrategy extends WarResearchStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getArmour();
    }

    public void upgrade() {
        this.getUser().getResearch().setArmour(this.getUser().getResearch().getArmour() + 1);
    }
}
