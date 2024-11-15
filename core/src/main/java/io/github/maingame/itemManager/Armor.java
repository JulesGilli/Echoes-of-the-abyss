package io.github.maingame.itemManager;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int lvl) {
        super(50 + 150 * lvl,"assets/Armor_t"+lvl+".png","assets/Armor_t"+lvl+"Buy.png");
        this.reductionDamage = 5 * lvl;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
