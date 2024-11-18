package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class HealPotion extends Consumable{
    public HealPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,
            "icons/items/potion/icon_potionHealth.png",
            "icons/items/potion/icon_potionHealth_bought.png",
            "icons/items/potion/icon_potionHealth_lock.png",
            stat);
    }

    @Override
    public  void applyItem(Player targetEntity){
        setIncreaseValue(50 + 10 * stat.getFloors());
        targetEntity.setHealth(targetEntity.getHealth() + getIncreaseValue());
        System.out.println("heal potion give you : " + getIncreaseValue() + " for " + timeDuration);
    }

    @Override
    public void resetItem(Player targetEntity){
        if (targetEntity.getHealth() > targetEntity.getMaxHealth()){
            targetEntity.setHealth(targetEntity.getMaxHealth());
        }
        System.out.println("HealPotion is actually reset");
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= 1;
    }
}
