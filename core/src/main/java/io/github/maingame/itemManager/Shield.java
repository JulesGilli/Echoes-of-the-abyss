package io.github.maingame.itemManager;

public class Shield extends Gear{
    protected int reductionDamage;
    public Shield(String name, int gold, int reductionDamage) {
        super(name,gold);
        this.reductionDamage = reductionDamage;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
