package io.github.maingame.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.core.GameStat;
import io.github.maingame.utils.Platform;

import java.util.List;

public class EnemyFactory {
    public static Enemy createRandomEnemy(Vector2 position, List<Platform> platforms, Player player, GameStat stat) {
        int floor = stat.getFloors();
        Enemy enemy;

        if (floor <= 3) {
            enemy = new Skeleton(position, platforms, player, stat);
        } else if (floor <= 6) {
            enemy = MathUtils.randomBoolean(0.7f) ?
                new Skeleton(position, platforms, player, stat) :
                new Goblin(position, platforms, player, stat);
        } else {
            float randomValue = MathUtils.random();
            if (randomValue < 0.5f) {
                enemy = new Skeleton(position, platforms, player, stat);
            } else if (randomValue < 0.8f) {
                enemy = new Goblin(position, platforms, player, stat);
            } else {
                enemy = new Mushroom(position, platforms, player, stat);
            }
        }

        return enemy;
    }

    public static BossEnemy createBoss(Vector2 position, List<Platform> platforms, Player player, GameStat stat) {
        return new BossEnemy(position, platforms, player, stat);
    }

    public static boolean isBossFloor(int floor) {
        return floor > 0 && floor % 5 == 0;
    }
}
