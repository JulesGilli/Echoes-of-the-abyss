package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;
import io.github.maingame.characterManager.Player;

public interface PotionManager {
    public void effectApply(Player targetEntity);
    public void resetEffect(Player targetEntity);
}
