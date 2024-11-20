package io.github.maingame.items;

import io.github.maingame.entities.Player;
import io.github.maingame.core.GameStat;

public class HealPotion extends Consumable {
    public HealPotion(GameStat stat) {
        super(80, 30,
            "icons/items/potion/icon_potionHealth.png",
            "icons/items/potion/icon_potionHealth_bought.png",
            "icons/items/potion/icon_potionHealth_lock.png",
            stat);

    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue(50 + 10 * stat.getFloors());
        targetEntity.setMaxHealth(targetEntity.getHealth() + getIncreaseValue());
        targetEntity.setHealth(targetEntity.getMaxHealth());
        System.out.println("heal potion give you : " + getIncreaseValue() + " for " + timeDuration);
    }

    @Override
    public void resetItem(Player targetEntity) {
        if (targetEntity.getHealth() > targetEntity.getMaxHealth() - getIncreaseValue()) {
            targetEntity.setMaxHealth(targetEntity.getHealth() - getIncreaseValue());
            targetEntity.setHealth(targetEntity.getMaxHealth());
            System.out.println("HealPotion is actually reset");
        }
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= 1;
    }
}
