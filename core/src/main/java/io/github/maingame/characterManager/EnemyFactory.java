package io.github.maingame.characterManager;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.utilsManager.GameStat;

import java.util.List;

public class EnemyFactory {
    public static Enemy createRandomEnemy(Vector2 position, List<Platform> platforms, Player player, GameStat stat) {
        float randomValue = MathUtils.random();
        if (randomValue < 0.5f) {
            return new Skeleton(position, platforms, player, stat);
        } else {
            return new Goblin(position, platforms, player, stat);
        }
    }
}
