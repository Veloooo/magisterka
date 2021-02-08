package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgricultureStrategy extends EconomicResearchStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getAgriculture();
    }

    public void upgrade() {
        this.getUser().getResearch().setAgriculture(this.getUser().getResearch().getAgriculture() + 1);
        setPeople();
    }
}
