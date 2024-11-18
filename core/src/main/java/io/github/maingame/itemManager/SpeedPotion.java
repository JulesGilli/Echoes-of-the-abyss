package io.github.maingame.itemManager;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class SpeedPotion extends Consumable {
    public SpeedPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,
            "icons/items/potion/icon_potionSpeed.png",
            "icons/items/potion/icon_potionSpeed_bought.png",
            "icons/items/potion/icon_potionSpeed_lock.png",
            stat);
        stat.setSpeedPotionUse(stat.getSpeedPotionUse() + 1);
    }

    @Override
    public  void applyItem(Player targetEntity){
        setIncreaseValue(targetEntity.getSpeed() * 0.6f);
        System.out.println("Speed Potion give you : " + targetEntity.getSpeed() + " for " + timeDuration);
        targetEntity.setSpeedBonus(getIncreaseValue());
    }

    @Override
    public void resetItem(Player targetEntity){
        System.out.println("Speed Potion reset, current speed: " + targetEntity.getSpeed());
        targetEntity.setSpeedBonus(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() > 3;
    }
}
