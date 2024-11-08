package io.github.maingame.itemManager;

public abstract class Item {
    public String name;
    public int value;

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
