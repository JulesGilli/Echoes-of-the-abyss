package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(int lvl) {
        super(50 + 150 * lvl,"assets/items/weapon/lvl" + lvl + ".png","assets/items/weapon/lvl" + lvl + "Buy.png", "assets/items/weapon/lvl" + lvl + "Lock.png" , lvl);
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

        return stat.getMaxFloors() > 3 * lvl && stat.getMaxFloors() < 15 * lvl;

    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
