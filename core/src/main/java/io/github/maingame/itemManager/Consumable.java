package io.github.maingame.itemManager;

public abstract class Consumable extends Item {
    private final int timeDuration;
    public Consumable(int value , int timeDuration, String textureName, String textureBuyName) {
        super(value, textureName, textureBuyName);
        this.timeDuration = timeDuration;
    }

    protected int getTimeDuration(){
        return timeDuration;
    }
}
