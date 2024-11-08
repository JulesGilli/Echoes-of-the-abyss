package io.github.maingame.itemManager;

public abstract class Item {
    protected String name;
    protected int gold;

    public Item(String name, int value) {
        this.name = name;
        this.gold = value;
    }
}
