package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public class SpeedPotion extends Consumable {
    private float SpeedIncrease;

    public SpeedPotion(int speedIncrease){
        super("SpeedPotion",50, speedIncrease );
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
