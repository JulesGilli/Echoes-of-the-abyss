package io.github.maingame.items;

import io.github.maingame.entities.Player;
import io.github.maingame.core.GameStat;

public interface ItemSolution {
    void applyItem(Player targetEntity);

    void resetItem(Player targetEntity);

    boolean isUnlocked(GameStat stat);
}
