package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

public class HealPotion extends Consumable{
    public HealPotion(int lvl) {
        super(50 + 10 * lvl, 50,"assets/SpeedPotion.png","assets/SpeedPotion-1.png" );
    }

    @Override
    public  void effectApply(Player targeEntity){
    }

    @Override
    public void resetEffect(Player targetEntity){
        targetEntity.setSpeedBonus(0);
    }
}
