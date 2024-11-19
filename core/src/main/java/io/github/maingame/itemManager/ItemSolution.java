package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public interface ItemSolution {
    void applyItem(Player targetEntity);

    void resetItem(Player targetEntity);

    boolean isUnlocked(GameStat stat);
}
