package pl.daniel.pawlowski.conquerorgame.model.strategy.research;

public class AttackStrategy extends WarResearchStrategy {

    public int getLevel() {
        return this.getUser().getResearch().getAttack();
    }

    public void upgrade() {
        this.getUser().getResearch().setAttack(this.getUser().getResearch().getAttack() + 1);
    }
}
