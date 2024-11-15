package io.github.maingame.itemManager;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public interface ItemSolution {
    public void applyItem(Player targetEntity);
    public void resetItem(Player targetEntity);
    public boolean isUnlocked(GameStat stat);
}
