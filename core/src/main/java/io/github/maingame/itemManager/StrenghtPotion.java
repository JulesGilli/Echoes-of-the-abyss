package io.github.maingame.itemManager;

import com.badlogic.gdx.Game;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class StrenghtPotion extends Consumable{
    private final int strengthBoost;
    public StrenghtPotion(int lvl) {
        super(50 + 10 * lvl, 30 ,"assets/StrengthPotion.png","assets/StrengthPotion-1.png" );
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
        return stat.getFloors() > 4;
    }
}
