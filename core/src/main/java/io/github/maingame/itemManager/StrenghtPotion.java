package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

public class StrenghtPotion extends Consumable{
    public StrenghtPotion(int lvl) {
        super(50 + 10 * lvl, 50 + 5*lvl ,"assets/SpeedPotion.png","assets/SpeedPotion-1.png" );
    }

    @Override
    public  void effectApply(Player targetEntity){
        targetEntity.
    }

    @Override
    public void resetEffect(Player targetEntity){
        targetEntity.setSpeedBonus(0);
    }
}
