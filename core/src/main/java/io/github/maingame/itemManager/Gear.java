package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public abstract class Gear extends Item {
    public Gear(String name, int gold) {
        super(name, gold);
    }

    public void applyItem(Entity target) {
        {
            if (this instanceof Sword){
                target.setAttackBonus(((Sword) this).getAttackIncrease());
            }
        }
            if (this instanceof Shield){
                target.setArmor(((Shield) this).reductionDamage);
            }
        }
}
