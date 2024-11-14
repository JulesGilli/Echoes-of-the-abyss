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

    public void applyGears(Entity entity) {
        for (Item item : items) {
            if (item instanceof Gear){
                ((Gear) item).applyItem(entity);
            }
        }
    }

    public void applyConsumable(Entity target) {
        for (Item item : items) {
            if (item instanceof Consumable){
                Consumable consumable = (Consumable) item;
                consumable.effectApply(target);
                new Thread(() -> {
                    try {
                        Thread.sleep(consumable.getTimeDuration()* 1000L);
                        consumable.resetEffect(target);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        }
    }

    public void resetInventory(Entity entity) {
        for (Item item : items) {
            removeItem(item);
        }
    }
}
