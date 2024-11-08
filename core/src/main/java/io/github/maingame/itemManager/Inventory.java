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
                target.setAttack(target.getAttack() + ((Sword) item).attackIncrease);
            }
            if (item instanceof Shield){
            }
        }
    }
}
