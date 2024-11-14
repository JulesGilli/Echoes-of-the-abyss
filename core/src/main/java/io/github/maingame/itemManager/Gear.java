package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public abstract class Gear extends Item {
    public Gear(int gold, String textureName, String textureBuyName) {
        super( gold, textureName, textureBuyName);
    }

    public void applyItem(Entity target) {
        {
            if (this instanceof Weapon){
                target.setAttackBonus(((Weapon) this).getAttackIncrease());
            }
        }
            if (this instanceof Armor){
                target.setArmor(((Armor) this).reductionDamage);
            }
        }
}
