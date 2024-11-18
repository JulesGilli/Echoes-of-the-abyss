package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(int lvl) {
        super(50 + 150 * lvl,"assets/items/weapon/lvl" + lvl + ".png","assets/items/weapon/lvl" + lvl + "Buy.png", "assets/items/weapon/lvl" + lvl + "Lock.png" , lvl);
        this.attackIncrease  = 5;
    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue(attackIncrease + 10 * lvl);
        targetEntity.setAttackDamage( targetEntity.getAttack() + (this).getIncreaseValue());
        System.out.println("applying weapon gear, currentAttack : " + targetEntity.getAttack());
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setAttackDamage(targetEntity.getAttack() - getIncreaseValue());
        System.out.println("reset weapon gear, current attack : " + targetEntity.getAttack());
    }

    @Override
    public boolean isUnlocked(GameStat stat) {

        return stat.getMaxFloors() >= 2 * lvl;

    }
}
