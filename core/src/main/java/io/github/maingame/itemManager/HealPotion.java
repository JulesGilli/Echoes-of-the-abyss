package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

public class HealPotion extends Consumable{
    private final int potionBonus;
    public HealPotion(int lvl) {
        super(50 + 10 * lvl, 50,"assets/HealPotion.png","assets/HealPotion-1.png" );
        this.potionBonus = 50 + 5 * lvl;
    }

    @Override
    public  void effectApply(Player targetEntity){ targetEntity.setHealth(targetEntity.getHealth() + potionBonus );
    }

    @Override
    public void resetEffect(Player targetEntity){
        if (targetEntity.getHealth() > targetEntity.getMaxHealth()){
            targetEntity.setHealth(targetEntity.getMaxHealth());
        }
    }
}
