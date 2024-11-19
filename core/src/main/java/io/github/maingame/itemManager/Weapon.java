package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Weapon extends Gear {
    protected int attackIncrease;

    public Weapon(int lvl) {
        super((int) (200 * Math.pow(2, lvl - 1)),
            "icons/items/weapon/icon_weapon" + lvl + ".png",
            "icons/items/weapon/icon_weapon" + lvl + "_bought.png",
            "icons/items/weapon/icon_weapon" + lvl + "_lock.png",
            lvl);
        this.attackIncrease = 5;
    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue(attackIncrease + 10 * lvl);
        targetEntity.setAttackDamage(targetEntity.getAttack() + (this).getIncreaseValue());
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
