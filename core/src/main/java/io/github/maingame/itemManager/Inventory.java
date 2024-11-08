package io.github.maingame.itemManager;

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

    public void applyGear(Character target){
        for (Item item : items) {
            if (item instanceof Sword){

            }
        }
    }
}
