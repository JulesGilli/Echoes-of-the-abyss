package io.github.maingame.itemManager;

import com.badlogic.gdx.Game;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class ArmorPotion extends Consumable {
    public ArmorPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,
            "icons/items/potion/icon_potionArmor.png",
            "icons/items/potion/icon_potionArmor_bought.png",
            "icons/items/potion/icon_potionArmor_lock.png",
            stat);
    }

    @Override
    public  void applyItem(Player targetEntity){
        System.out.println("applying Armor potion, current armor bonus: " + getIncreaseValue());
        System.out.println("time duration : " + timeDuration);
        setIncreaseValue(stat.getDeaths());
        targetEntity.setArmor(targetEntity.getArmor() + getIncreaseValue());
    }

    @Override
    public void resetItem(Player targetEntity){
        targetEntity.setArmor(0);
        System.out.println("reset Armor potion, current armor bonus: " + targetEntity.getArmor());
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= 6;
    }
}
