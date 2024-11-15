package io.github.maingame.itemManager;

import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(int lvl) {
        super(50 + 150 * lvl,"assets/Weapon_t"+lvl+".png","assets/Weapon_t"+lvl+"_Buy.png");
        this.attackIncrease = 5 + 10 * lvl;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
