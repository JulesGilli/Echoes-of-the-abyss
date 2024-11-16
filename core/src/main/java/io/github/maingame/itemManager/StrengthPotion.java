package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class StrengthPotion extends Consumable{
    private final int strengthBoost;
    public StrengthPotion(int lvl) {
        super(50 + 10 * lvl, 30 ,"assets/items/strengthPotion.png","assets/items/strengthPotionBuy.png","assets/items/strengthPotionLock.png");
        this.strengthBoost = 25 + 10 * lvl;
    }

    @Override
    public void applyItem(Player targetEntity) {
        targetEntity.setAttackDamage(targetEntity.getAttack() + strengthBoost);
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setAttackDamage(targetEntity.getAttack() - strengthBoost);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() > 4;
    }
}
