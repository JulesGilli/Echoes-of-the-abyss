package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class StrengthPotion extends Consumable{

    public StrengthPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 10 ,"assets/items/strengthPotion.png","assets/items/strengthPotionBuy.png","assets/items/strengthPotionLock.png", stat);
    }

    @Override
    public void applyItem(Player targetEntity) {
        setIncreaseValue(10 * stat.getFloors());
        System.out.println("applying strength potion, current attack bonus: " + getIncreaseValue());
        System.out.println("time duration : " + timeDuration);
        targetEntity.setAttackBonus(getIncreaseValue());
        System.out.println("Attack with bonus : " + targetEntity.getAttack());
    }

    @Override
    public void resetItem(Player targetEntity) {
        System.out.println("reset strength potion, reset bonus: " + getIncreaseValue());
        System.out.println("time duration : " + timeDuration);
        targetEntity.setAttackBonus(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() >= 3;
    }
}
