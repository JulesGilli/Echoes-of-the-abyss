package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public class SpeedPotion extends Consumable {
    private float SpeedIncrease;

    public SpeedPotion(int lvl){
        super(50 + 10 * lvl, 50,"assets/SpeedPotion.png","assets/SpeedPotion-1.png" );
        this.SpeedIncrease = 10 + 5 * lvl;
    }

    @Override
    public  void effectApply(Entity targeEntity){
        targeEntity.setSpeedBonus(SpeedIncrease);
    }

    @Override
    public void resetEffect(Entity targetEntity){
        targetEntity.setSpeedBonus(0);
    }
}
