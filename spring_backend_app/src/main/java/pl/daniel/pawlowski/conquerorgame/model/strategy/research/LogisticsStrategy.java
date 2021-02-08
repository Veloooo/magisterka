package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class LogisticsStrategy extends WarResearchStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getLogistics();
    }

    public void upgrade() {
        this.getUser().getResearch().setLogistics(this.getUser().getResearch().getLogistics() + 1);
    }
}
