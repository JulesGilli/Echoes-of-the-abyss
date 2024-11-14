package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

public class StrenghtPotion extends Consumable{
    private final int strengthBoost;
    public StrenghtPotion(int lvl) {
        super(50 + 10 * lvl, 30 ,"assets/StrengthPotion.png","assets/StrengthPotion-1.png" );
        this.strengthBoost = 25 + 10 * lvl;
    }

    @Override
    public  void effectApply(Player targetEntity){
        targetEntity.setAttackDamage(targetEntity.getAttack() + strengthBoost);
    }

    @Override
    public void resetEffect(Player targetEntity){
        targetEntity.setAttackDamage(targetEntity.getAttack() - strengthBoost);
    }
}
