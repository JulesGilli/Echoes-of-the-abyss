package io.github.maingame.itemManager;

import com.badlogic.gdx.Game;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class ArmorPotion extends Consumable {
    private final int armorBonus;
    public ArmorPotion(GameStat stat) {
        super(50 + 10 * stat.getMaxFloors(), 50,"assets/items/armorPotion.png","assets/items/armorPotionBuy.png","assets/items/armorPotionLock.png" );
        this.armorBonus = 10 + 5 * stat.getDeaths();
    }

    @Override
    public  void applyItem(Player targetEntity){
        targetEntity.setArmor(targetEntity.getArmor() + armorBonus);
    }

    @Override
    public void resetItem(Player targetEntity){
        targetEntity.setArmor(targetEntity.getArmor() - armorBonus);
    }

    @Override
    public boolean isUnlocked(GameStat stat) {
        return stat.getMaxFloors() > 6;
    }
}
