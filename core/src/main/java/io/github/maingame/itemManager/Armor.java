package io.github.maingame.itemManager;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int gold, int reductionDamage, String textureName, String textureBuyName) {
        super(gold, textureName, textureBuyName);
        this.reductionDamage = reductionDamage;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
