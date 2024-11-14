package io.github.maingame.itemManager;

public abstract class Consumable extends Item implements PotionManager {
    private int timeDuration;
    public Consumable(String name, int value , int timeDuration, String textureName, String textureBuyName) {
        super(value, textureName, textureBuyName);
        this.timeDuration = timeDuration;
    }

    protected int getTimeDuration(){
        return timeDuration;
    }
}
