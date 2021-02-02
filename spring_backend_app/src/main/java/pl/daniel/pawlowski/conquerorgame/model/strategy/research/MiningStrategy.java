package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class MiningStrategy extends ResearchUpgradeStrategy {

    public int getLevel(){
        return this.getUser().getResearch().getMining();
    }
    public void upgrade(){
        this.getUser().getResearch().setMining(this.getUser().getResearch().getMining() + 1);
    }
}
