package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int lvl) {
        super(50 + 150 * lvl,"assets/Armor_t"+lvl+".png","assets/Armor_t"+lvl+"Buy.png");
        this.reductionDamage = 5 * lvl;
    }

    @Override
    public void applyItem(Player targetEntity) {
        targetEntity.setArmor(reductionDamage);
    }

    @Override
    public void resetItem(Player targetEntity) {
        targetEntity.setArmor(0);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return true;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
