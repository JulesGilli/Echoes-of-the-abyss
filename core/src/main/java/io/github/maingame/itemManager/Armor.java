package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class Armor extends Gear{
    protected int reductionDamage;
    public Armor(int lvl) {
        super(50 + 150 * lvl,"assets/items/armor/lvl" + lvl + ".png","assets/items/armor/lvl" + lvl + "Buy.png", "assets/items/armor/lvl" + lvl + "Lock.png" ,lvl);
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

        return stat.getMaxFloors() > 5 * lvl && stat.getFloors() < 10 * lvl;
    }

    public int getReductionDamage() {
        return reductionDamage;
    }
}
