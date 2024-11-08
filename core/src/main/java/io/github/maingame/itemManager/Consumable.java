package io.github.maingame.itemManager;

public abstract class Consumable extends Item implements PotionManager {
    private int timeDuration;
    public Consumable(String name, int value , int timeDuration) {
        super(name, value);
        this.timeDuration = timeDuration;
    }

    protected int getTimeDuration(){
        return timeDuration;
    }
}
