package io.github.maingame.characterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.itemManager.Gear;
import io.github.maingame.itemManager.Inventory;
import io.github.maingame.itemManager.Item;

import java.util.List;

public class Player extends Entity {
    private final float maxStamina = 100;
    private final Inventory inventory;
    private boolean isDead = false;
    private int leftKey;
    private int rightKey;
    private int jumpKey;
    private int attackKey;
    private int rollKey;
    private final int potionKey;
    private boolean isRolling = false;
    private float rollTimer = 0f;
    private float stamina = 100;

    public Player(Vector2 position, List<Platform> platforms, int leftKey, int rightKey, int jumpKey, int attackKey, int rollKey, int potionKey) {
        super(position,
            new AnimationManager(
                "atlas/player/sprite_player_walk.png",
                "atlas/player/sprite_player_idle.png",
                "atlas/player/sprite_player_jump.png",
                "atlas/player/sprite_player_attack.png",
                "atlas/player/sprite_player_death.png",
                "atlas/player/sprite_player_roll.png",
                "atlas/player/sprite_player_hit.png",
                120, 80, 0.1f, 0.04f), 100, 100, 25);

        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.jumpKey = jumpKey;
        this.attackKey = attackKey;
        this.rollKey = rollKey;
        this.potionKey = potionKey;

        this.inventory = new Inventory();

        this.initialPosition = new Vector2(position);
        this.initialHealth = health;
        this.initialGold = gold;
        this.initialAttack = attackDamage;

        this.speed = 500;
        this.jumpVelocity = 1000;
        this.gravity = -25;
        this.platforms = platforms;
        this.renderWidth = 450;
        this.renderHeight = 300;
        this.attackRange = 50;

    }

    public void update(float delta, List<Enemy> enemies, float leftBoundary, float rightBoundary) {
        if (health <= 0 && !isDead) {
            isDead = true;
            isAttacking = false;
            isRolling = false;
            animationTime = 0f;
            reset();

        }

        if (isDead) {
            animationTime += delta;
        } else if (isRolling) {
            rollTimer += delta;
            animationTime += delta;
            float rollDuration = 0.5f;
            if (rollTimer >= rollDuration) {
                isRolling = false;
                rollTimer = 0f;
            } else {
                float rollSpeed = 700;
                velocity.x = isLookingRight ? rollSpeed : -rollSpeed;
                position.add(velocity.cpy().scl(delta));
            }
        } else {
            handleInput(delta);
            animationTime += delta;

            position.x = MathUtils.clamp(position.x, leftBoundary, rightBoundary - renderWidth);

            if (isAttacking) {
                for (Enemy enemy : enemies) {
                    if (isCollidingWith(enemy, attackRange) && isEnemyInFront(enemy)) {
                        enemy.receiveDamage(getAttack());
                    }
                }
                checkAttackFinish();
            } else {
                applyGravity();
                for (Platform platform : platforms) {
                    if (isOnPlatform(platform) && velocity.y <= 0) {
                        position.y = platform.getBounds().y + platform.getBounds().height;
                        velocity.y = 0;
                        isJumping = false;
                        break;
                    }
                }
                position.add(velocity.cpy().scl(delta));
                checkOnFloor();
            }
        }

        float staminaRegenRate = 10f;
        stamina = Math.min(maxStamina, stamina + staminaRegenRate * delta);

    }

    private boolean isEnemyInFront(Enemy enemy) {
        boolean inFront = (isLookingRight && enemy.getPosition().x > position.x) ||
            (!isLookingRight && enemy.getPosition().x < position.x);
        Gdx.app.log("Player", "Enemy " + (inFront ? "is" : "is not") + " in front.");
        return inFront;
    }


    @Override
    public void receiveDamage(float damage) {
        if (isDead || isRolling) return;

        float reducedDamage = Math.max(0, damage - armor);
        this.health -= reducedDamage;

        if (this.health <= 0) {
            this.health = 0;
            isDead = true;
            animationTime = 0f;
        }
    }


    public void handleInput(float delta) {
        if (isAttacking || isRolling || isDead) return;

        if (Gdx.input.isKeyPressed(leftKey)) {
            velocity.x = -speed;
            isLookingRight = false;
        } else if (Gdx.input.isKeyPressed(rightKey)) {
            velocity.x = speed;
            isLookingRight = true;
        } else {
            idle();
        }

        if (Gdx.input.isKeyJustPressed(jumpKey) && !isJumping && !isRolling) {
            velocity.y = jumpVelocity;
            isJumping = true;
        }

        if (Gdx.input.isKeyJustPressed(attackKey) && !isAttacking && !isJumping) {
            float staminaCostAttack = 20f;
            if (stamina >= staminaCostAttack) {
                stamina -= staminaCostAttack;
                isAttacking = true;
                animationTime = 0f;
            }
        }

        if (Gdx.input.isKeyJustPressed(rollKey) && !isRolling && !isJumping) {
            float staminaCostRoll = 15f;
            if (stamina >= staminaCostRoll) {
                stamina -= staminaCostRoll;
                isRolling = true;
                animationTime = 0f;
                rollTimer = 0f;
            }
        }
        if (Gdx.input.isKeyJustPressed(potionKey)) {
            inventory.applyConsumable(this);
        }
    }

    @Override
    public TextureRegion getCurrentFrame() {
        if (isDead) {
            return flipAnimationCheck(animation.getDeathCase().getKeyFrame(animationTime, false));
        } else if (isRolling) {
            return flipAnimationCheck(animation.getRollCase().getKeyFrame(animationTime, false));
        } else if (isAttacking) {
            return flipAnimationCheck(animation.getAttackCase().getKeyFrame(animationTime, true));
        } else if (isJumping) {
            return flipAnimationCheck(animation.getJumpCase().getKeyFrame(animationTime, true));
        } else if (velocity.x != 0) {
            return flipAnimationCheck(animation.getWalkCase().getKeyFrame(animationTime, true));
        } else {
            return flipAnimationCheck(animation.getIdleCase().getKeyFrame(animationTime, true));
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y, renderWidth, renderHeight);
    }

    public void prepareForNewGame() {
        health = maxHealth;
        position.set(initialPosition);
        velocity.set(0, 0);
        isDead = false;
    }


    public void reset() {
        position.set(initialPosition);
        health = initialHealth;
        attackDamage = initialAttack;
        isDead = false;
        isAttacking = false;
        velocity.set(0, 0);
        animationTime = 0f;

        for (Item item : inventory.getItems()) {
            if (item instanceof Gear) {
                item.applyItem(this);
            }
        }
    }

    public boolean isDeathAnimationFinished() {
        return isDead && animationTime >= animation.getDeathCase().getAnimationDuration();
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public void setJumpKey(int jumpKey) {
        this.jumpKey = jumpKey;
    }

    public void setAttackKey(int attackKey) {
        this.attackKey = attackKey;
    }

    public void setRollKey(int rollKey) {
        this.rollKey = rollKey;
    }

    public float getStamina() {
        return stamina;
    }

    public float getMaxStamina() {
        return maxStamina;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
