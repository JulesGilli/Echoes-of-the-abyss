package io.github.maingame.itemManager;

import io.github.maingame.utilsManager.GameStat;

public abstract class Consumable extends Item {
    protected final GameStat stat;
    protected final int timeDuration;

    public Consumable(int value, int timeDuration, String textureName, String textureBuyName, String textureLockName, GameStat stat) {
        super(value, textureName, textureBuyName, textureLockName);
        this.timeDuration = timeDuration;
        this.stat = stat;
    }

    protected int getTimeDuration() {
        return timeDuration;
    }
}
