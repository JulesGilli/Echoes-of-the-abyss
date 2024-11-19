package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class ArmorPotion extends Consumable {
    public ArmorPotion(GameStat stat) {
        super(200, 30,
            "icons/items/potion/icon_potionArmor.png",
            "icons/items/potion/icon_potionArmor_bought.png",
            "icons/items/potion/icon_potionArmor_lock.png",
            stat);

    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue(10 * stat.getFloors());
        targetEntity.setArmor(targetEntity.getArmor() + getIncreaseValue());
        System.out.println("applying Armor potion, current armor: " + targetEntity.getArmor());
        System.out.println("time duration : " + timeDuration);
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setArmor(0);
        System.out.println("reset Armor potion, current armor : " + targetEntity.getArmor());
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= 7;
    }
}
