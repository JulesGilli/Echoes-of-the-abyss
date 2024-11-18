package io.github.maingame.characterManager;

import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.utilsManager.GameStat;

import java.util.List;

public class Mushroom extends Enemy {

    private float attackDelayTimer = 0f;

    public Mushroom(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {
        super(
            position,
            platforms,
            player,
            gameStat,
            new AnimationManager(
                "atlas/mushroom/sprite_mushroom_walk.png",
                "atlas/mushroom/sprite_mushroom_idle.png",
                "atlas/mushroom/sprite_mushroom_walk.png",
                "atlas/mushroom/sprite_mushroom_attack.png",
                "atlas/mushroom/sprite_mushroom_death.png",
                "atlas/mushroom/sprite_mushroom_walk.png",
                150, 101, 0.2f, 0.15f
            ),
            80,
            200,
            10,
            100,
            1.5f
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
