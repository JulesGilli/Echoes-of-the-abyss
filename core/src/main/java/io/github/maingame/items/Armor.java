package io.github.maingame.items;

import io.github.maingame.entities.Entity;
import io.github.maingame.entities.Player;
import io.github.maingame.core.GameStat;

public class Armor extends Gear {
    protected int reductionDamage;

    public Armor(int lvl) {
        super((int) (300 * Math.pow(2, lvl - 1)),
            "icons/items/armor/icon_armor" + lvl + ".png",
            "icons/items/armor/icon_armor" + lvl + "_bought.png",
            "icons/items/armor/icon_armor" + lvl + "_lock.png",
            lvl);
        this.reductionDamage = 5;
        this.unlockFloor = 3 * lvl;
    }

    @Override
    public void applyItem(Entity targetEntity) {
        setIncreaseValue((float) (10 * Math.pow(2, lvl - 1) * rarity.getStatMultiplier()));
        targetEntity.setArmor(getIncreaseValue());
        targetEntity.setMaxHealth(targetEntity.getMaxHealth() + getIncreaseValue() * 10);
        targetEntity.setHealth(targetEntity.getMaxHealth());
    }

    @Override
    public void resetItem(Entity targetEntity) {
        targetEntity.setArmor(0);
        targetEntity.setMaxHealth(targetEntity.getMaxHealth() - getIncreaseValue() * 10);
        targetEntity.setHealth(targetEntity.getMaxHealth());
    }

    @Override
    public boolean isUnlocked(GameStat stat) {

        return stat.getMaxFloors() >= unlockFloor;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
