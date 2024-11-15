package io.github.maingame.itemManager;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class SpeedPotion extends Consumable {
    private final float SpeedIncrease;

    public SpeedPotion(int lvl){
        super(50 + 10 * lvl, 50,"assets/SpeedPotion.png","assets/SpeedPotion-1.png" );
        this.SpeedIncrease = 10 + 5 * lvl;
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
        return stat.getFloors() > 3;
    }
}
