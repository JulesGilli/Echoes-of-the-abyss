package io.github.maingame.characterManager;

import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.utilsManager.GameStat;

import java.util.List;

public class Goblin extends Enemy {
    public Goblin(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {
        super(
            position,
            platforms,
            player,
            gameStat,
            new AnimationManager(
                "GoblinWalk.png",
                "GoblinIdle.png",
                "GoblinWalk.png",
                "GoblinAttack.png",
                "GoblinDeath.png",
                "GoblinWalk.png",
                150, 101, 0.15f, 0.1f
            ),
            30, // Health
            400, // Speed
            5, // Attack damage
            150, // Range
            1.2f // Attack cooldown
        );
    }

    @Override
    public void makeAction(float delta) {
        if (isDead || isAttacking) return;

        if (inRange()) {
            attack();
        } else {
            walk();
        }
    }

    public boolean inRange() {
        float distance = Math.abs(target.getPosition().x - position.x);
        return distance <= range;
    }

    public void walk() {
        if (target.getPosition().x > position.x) {
            moveLaterally(speed);
        } else {
            moveLaterally(-speed);
        }
    }
}
