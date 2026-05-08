package io.github.maingame.items;

import io.github.maingame.entities.Entity;
import io.github.maingame.entities.Player;
import io.github.maingame.core.GameStat;

public class ArmorPotion extends Consumable {
    public ArmorPotion(GameStat stat) {
        super(200, 30,
            "icons/items/potion/icon_potionArmor.png",
            "icons/items/potion/icon_potionArmor_bought.png",
            "icons/items/potion/icon_potionArmor_lock.png",
            stat);
        this.unlockFloor = 7;
    }

    @Override
    public String getDisplayName() {
        return "Armor Potion";
    }

    @Override
    public String getStatDescription() {
        return "DEF +" + (10 * stat.getFloors());
    }

    @Override
    public void applyItem(Entity targetEntity) {
        setIncreaseValue(10 * stat.getFloors());
        targetEntity.setArmor(targetEntity.getArmor() + getIncreaseValue());
    }

    @Override
    public void resetItem(Entity targetEntity) {
        targetEntity.setArmor(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= unlockFloor;
    }
}
