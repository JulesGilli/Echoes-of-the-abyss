package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(int lvl) {
        super(50 + 150 * lvl,"assets/Weapon_t"+lvl+".png","assets/Weapon_t"+lvl+"_Buy.png");
        this.attackIncrease = 5 + 10 * lvl;
    }

    @Override
    public void applyItem(Entity target) {
        target.setAttackBonus((this).getAttackIncrease());
    }

    @Override
    public void resetItem(Entity target) {
        target.setAttackBonus(0);
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
