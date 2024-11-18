package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public boolean inInventory(Item item) {
        return items.contains(item);
    }

    public boolean containWeapon(){
        for (Item item : items){
            if (item instanceof Weapon){
                return true;
            }
        }
        return false;
    }

    public boolean containArmor(){
        for (Item item : items){
            if (item instanceof Armor){
                return true;
            }
        }
        return false;
    }

    public boolean containConsumable(){
        for (Item item : items){
            if (item instanceof Consumable){
                return true;
            }
        }
        return false;
    }

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

    public void clear(Player entity) {
        for (Item item : items) {
            if (item instanceof Gear){
                ((Gear) item).resetItem(entity);
            }
            items.remove(item);
        }
    }

    public void applyConsumable(Player target) {
        for (Item item : items) {
            if (item instanceof Consumable){
                Consumable consumable = (Consumable) item;
                consumable.applyItem(target);
                new Thread(() -> {
                    try {
                        Thread.sleep(consumable.getTimeDuration()* 1000L);
                        consumable.resetItem(target);
                        removeItem(consumable);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

    public List<Item> getItems() {
        return items;
    }


}
