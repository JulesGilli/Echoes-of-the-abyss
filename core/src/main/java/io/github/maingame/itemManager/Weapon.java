package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(int lvl) {
        super(50 + 150 * lvl,"assets/Weapon_t"+lvl+".png","assets/Weapon_t"+lvl+"_Buy.png");
        this.attackIncrease = 5 + 10 * lvl;
    }

    @Override
    public void applyItem(Player targetEntity) {
        targetEntity.setAttackBonus((this).getAttackIncrease());
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setAttackBonus(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return true;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
