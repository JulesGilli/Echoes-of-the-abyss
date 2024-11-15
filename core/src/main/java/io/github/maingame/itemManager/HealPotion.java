package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class HealPotion extends Consumable{
    private final int potionBonus;
    public HealPotion(int lvl) {
        super(50 + 10 * lvl, 50,"assets/HealPotion.png","assets/HealPotion-1.png" );
        this.potionBonus = 50 + 5 * lvl;
    }

    @Override
    public  void applyItem(Player targetEntity){ targetEntity.setHealth(targetEntity.getHealth() + potionBonus );
    }

    @Override
    public void resetItem(Player targetEntity){
        if (targetEntity.getHealth() > targetEntity.getMaxHealth()){
            targetEntity.setHealth(targetEntity.getMaxHealth());
        }
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() > 1;
    }
}
