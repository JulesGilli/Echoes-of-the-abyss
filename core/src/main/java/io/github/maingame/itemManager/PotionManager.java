package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public interface PotionManager {
    public  void effectApply(Entity targeEntity);
    public void resetEffect(Entity targetEntity);
}
