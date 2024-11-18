package io.github.maingame.characterManager;

import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.utilsManager.GameStat;

import java.util.List;

public class FlyingEye extends Enemy {

    private float attackDelayTimer = 0f;

    public FlyingEye(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {
        super(
            position,
            platforms,
            player,
            gameStat,
            new AnimationManager(
                "atlas/flyingeye/sprite_flyingeye_walk.png",
                "atlas/flyingeye/sprite_flyingeye_idle.png",
                "atlas/flyingeye/sprite_flyingeye_walk.png",
                "atlas/flyingeye/sprite_flyingeye_attack.png",
                "atlas/flyingeye/sprite_flyingeye_death.png",
                "atlas/flyingeye/sprite_flyingeye_walk.png",
                120, 101, 0.1f, 0.1f
            ),
            20,
            500,
            8,
            250,
            1.0f
        );
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
