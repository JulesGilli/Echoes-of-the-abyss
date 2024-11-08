package io.github.maingame.itemManager;

public abstract class Consumable extends Item {
    private float timeDuration;
    public Consumable(String name, int value , float timeDuration) {
        super(name, value);
        this.timeDuration = timeDuration;
    }
}
