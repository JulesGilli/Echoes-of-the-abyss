package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public interface GearManager {
    public void applyItem(Entity target);
    public void resetItem(Entity target);
}
