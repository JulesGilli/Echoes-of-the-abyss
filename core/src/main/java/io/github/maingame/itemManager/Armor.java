package io.github.maingame.itemManager;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(String name, int gold, int reductionDamage, String textureName, String textureBuyName) {
        super(name,gold, textureName, textureBuyName);
        this.reductionDamage = reductionDamage;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
