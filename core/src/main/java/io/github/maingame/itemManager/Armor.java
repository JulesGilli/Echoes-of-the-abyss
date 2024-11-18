package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int lvl) {
        super((int) (300 * Math.pow(2, lvl - 1)),
            "assets/items/armor/lvl" + lvl + ".png",
            "assets/items/armor/lvl" + lvl + "Buy.png",
            "assets/items/armor/lvl" + lvl + "Lock.png",
            lvl);
        this.reductionDamage = 5;
    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue((int) (10 * Math.pow(2, lvl - 1)));
        targetEntity.setArmor(getIncreaseValue());
        targetEntity.setMaxHealth(targetEntity.getMaxHealth() + getIncreaseValue() * 10);
        targetEntity.setHealth(targetEntity.getMaxHealth());
        System.out.println("applying armor gear, current damage reduction : " + targetEntity.getArmor());
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setArmor(0);
        targetEntity.setMaxHealth(targetEntity.getMaxHealth() - getIncreaseValue() * 10);
        targetEntity.setHealth(targetEntity.getMaxHealth());
        System.out.println("reset health of armor, current health : " + targetEntity.getArmor());
        System.out.println("reset armor gear, current damage reduction : " + targetEntity.getArmor());
    }

    @Override
    public boolean isUnlocked(GameStat stat) {

        return stat.getMaxFloors() >= 3 * lvl;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
