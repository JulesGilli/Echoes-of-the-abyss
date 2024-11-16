package io.github.maingame.itemManager;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class SpeedPotion extends Consumable {
    private final float SpeedIncrease;

    public SpeedPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,"assets/items/speedPotion.png","assets/items/speedPotionBuy.png","assets/items/speedPotionLock.png" );
        this.SpeedIncrease = Math.min(10 + (0.3f * stat.getSpeedPotionUse()),50);
        stat.setSpeedPotionUse(stat.getSpeedPotionUse() + 1);
    }

    @Override
    public  void applyItem(Player targetEntity){
        targetEntity.setSpeedBonus(SpeedIncrease);
    }

    @Override
    public void resetItem(Player targetEntity){
        targetEntity.setSpeedBonus(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() > 3;
    }
}
