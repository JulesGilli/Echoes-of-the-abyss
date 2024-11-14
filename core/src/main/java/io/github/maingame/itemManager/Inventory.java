package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;

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

    public void applyGears(Player entity) {
        for (Item item : items) {
            if (item instanceof Gear){
                ((Gear) item).applyItem(entity);
            }
        }
    }

    public void applyConsumable(Player target) {
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

    public void resetInventory(Player entity) {
        for (Item item : items) {
            removeItem(item);
        }
    }
}
