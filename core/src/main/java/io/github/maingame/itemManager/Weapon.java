package io.github.maingame.itemManager;

import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(String name, int gold, int lvl) {
        super(gold,"Weapon_t"+lvl+".png","Weapon_t"+lvl+"_buy.png");
        this.attackIncrease = 15 + 5 * lvl;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
