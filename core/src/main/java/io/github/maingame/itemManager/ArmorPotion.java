package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

public class ArmorPotion extends Consumable {
    private final int armorBonus;
    public ArmorPotion(int lvl) {
        super(50 + 10 * lvl, 50,"assets/ArmorPotion.png","assets/ArmorPotion-1.png" );
        this.armorBonus = 10 + 5 * lvl;
    }

    @Override
    public  void effectApply(Player targetEntity){
        targetEntity.setArmor(targetEntity.getArmor() + armorBonus);
    }

    @Override
    public void resetEffect(Player targetEntity){
        targetEntity.setArmor(targetEntity.getArmor() - armorBonus);
    }
}
