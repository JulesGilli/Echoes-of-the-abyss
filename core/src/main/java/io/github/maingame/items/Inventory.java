package io.github.maingame.items;

import io.github.maingame.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public boolean inInventory(Item item) {
        return items.contains(item);
    }

    public boolean containWeapon() {
        for (Item item : items) {
            if (item instanceof Weapon) {
                return true;
            }
        }
        return false;
    }

    public boolean containArmor() {
        for (Item item : items) {
            if (item instanceof Armor) {
                return true;
            }
        }
        return false;
    }

    public boolean containConsumable() {
        for (Item item : items) {
            if (item instanceof Consumable) {
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
            if (item instanceof Gear) {
                item.applyItem(entity);
            }
        }
    }

    public void clear(Player entity) {
        for (Item item : items) {
            if (item instanceof Gear) {
                item.resetItem(entity);
            }
        }
        items.clear();
    }

    private Consumable activeConsumable = null;
    private float consumableTimer = 0f;
    private Player consumableTarget = null;

    public void applyConsumable(Player target) {
        if (activeConsumable != null) return;

        for (Item item : items) {
            if (item instanceof Consumable) {
                activeConsumable = (Consumable) item;
                consumableTarget = target;
                consumableTimer = activeConsumable.getTimeDuration();
                activeConsumable.applyItem(target);
                removeItem(activeConsumable);
                break;
            }
        }
    }

    public void updateConsumableTimer(float delta) {
        if (activeConsumable == null) return;

        consumableTimer -= delta;
        if (consumableTimer <= 0) {
            activeConsumable.resetItem(consumableTarget);
            activeConsumable = null;
            consumableTarget = null;
        }
    }

    public String getPotionTexture() {
        for (Item item : items) {
            if (item instanceof Consumable) {
                return item.getClass().getSimpleName();
            }
        }
        return null;
    }


    public List<Item> getItems() {
        return items;
    }


}
