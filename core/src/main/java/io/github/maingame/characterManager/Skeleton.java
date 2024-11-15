package io.github.maingame.characterManager;

import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.utilsManager.GameStat;

import java.util.List;

public class Skeleton extends Enemy {

    private float scaleFactor;

    public Skeleton(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {
        super(
            position,
            platforms,
            player,
            gameStat,
            new AnimationManager(
                "SkeletonWalk.png",
                "SkeletonIdle.png",
                "SkeletonWalk.png",
                "SkeletonAttack.png",
                "SkeletonDeath.png",
                "SkeletonWalk.png",
                150, 101, 0.1f, 0.1f
            ),
            50 * (1 + (gameStat.getFloors() / 50f)),
            300,
            10 * (1 + (gameStat.getFloors() / 50f)),
            200,
            1.5f
        );

        this.scaleFactor = 5;

        this.renderWidth *= scaleFactor;
        this.renderHeight *= scaleFactor;
    }

    @Override
    public void makeAction(float delta) {
        if (isDead || isAttacking) return;

        if (inRange()) {
            attackDelayTimer += delta;
            if (attackDelayTimer >= attackDelay && timeSinceLastAttack >= attackCooldown) {
                attack();
                timeSinceLastAttack = 0f;
                attackDelayTimer = 0f;
                hasHitPlayer = false;
            } else {
                idle();
            }
        } else {
            attackDelayTimer = 0f;

            if (!isPlayerInFront()) {
                isLookingRight = target.getPosition().x > position.x;
            }
            walk();
        }
    }

}

