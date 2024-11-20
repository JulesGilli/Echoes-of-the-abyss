package io.github.maingame.items;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private final List<Item> items = new ArrayList<>();

    public ItemList() {
        for (int i = 1; i < 6; i++) {
            items.add(new Weapon(i));
        }
        for (int i = 6; i < 12; i++) {
            items.add(new Weapon(5));
        }
    }
}
