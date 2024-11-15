package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int lvl) {
        super(50 + 150 * lvl,"assets/Armor_t"+lvl+".png","assets/Armor_t"+lvl+"Buy.png");
        this.reductionDamage = 5 * lvl;
    }

    @Override
    public void applyItem(Entity target) {
        target.setArmor(reductionDamage);
    }

    @Override
    public void resetItem(Entity target) {
        target.setArmor(0);
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
