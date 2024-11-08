package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void applyGear(Entity target) {
        for (Item item : items) {
            if (item instanceof Sword){
                target.setAttackIncrease(((Sword) item).getAttackIncrease());
            }
            if (item instanceof Shield){
                target.setArmor(((Shield) item).reductionDamage);
            }
        }
    }

    public void applyConsumable(Entity target , float delta) {
        for (Item item : items) {
            if (item instanceof SpeedPotion){
                target.setSpeedIncrease(((SpeedPotion) item).getSpeedIncrease());
            }
        }
    }
}
