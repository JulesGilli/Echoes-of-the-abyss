package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class HealPotion extends Consumable{
    private final int potionBonus;
    public HealPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,"assets/items/healPotion.png","assets/items/healPotionBuy.png","assets/items/healPotionLock.png" );
        this.potionBonus = 50 + 5 * stat.getDeaths();
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
