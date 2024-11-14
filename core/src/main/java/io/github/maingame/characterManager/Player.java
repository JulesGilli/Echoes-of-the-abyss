package io.github.maingame.characterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;

public class Player extends Entity {
    private boolean hasHitEnemy = false;
    private boolean isDead = false;

    private int leftKey;
    private int rightKey;
    private int jumpKey;
    private int attackKey;
    private int rollKey;

    private boolean isRolling = false;
    private float rollDuration = 0.7f;
    private float rollTimer = 0f;
    private float rollSpeed = 600;

    public Player(Vector2 position, List<Platform> platforms, int leftKey, int rightKey, int jumpKey, int attackKey, int rollKey){
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png",
            "_Attack.png","_Death.png","_Roll.png", 120, 80, 0.1f,0.06f),
            300,100, 25);

        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.jumpKey = jumpKey;
        this.attackKey = attackKey;
        this.rollKey = rollKey;

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
        this.attackRange = 200;
    }

    public void update(float delta, List<Enemy> enemies) {
        if (health <= 0 && !isDead) {
            isDead = true;
            isAttacking = false;
            isRolling = false;
            animationTime = 0f;
        }

        if (isDead) {
            animationTime += delta;
        } else if (isRolling) {
            rollTimer += delta;
            animationTime += delta;
            if (rollTimer >= rollDuration) {
                isRolling = false;
                rollTimer = 0f;
            } else {
            velocity.x = isLookingRight ? rollSpeed : -rollSpeed;
            position.add(velocity.cpy().scl(delta));
        }
        } else {
            handleInput(delta);
            animationTime += delta;

            if (isAttacking) {
                if (!hasHitEnemy) {
                    for (Enemy enemy : enemies) {
                        if (isCollidingWith(enemy, attackRange)) {
                            enemy.receiveDamage(getAttack());
                            hasHitEnemy = true;
                            break;
                        }
                    }
                }
                checkAttackFinish();
            } else {
                hasHitEnemy = false;
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
    }

    @Override
    public void receiveDamage(float damage) {
        if (isDead || isRolling) return;

        this.health -= damage - armor;

        if (this.health <= 0) {
            isDead = true;
            animationTime = 0f;
        }
    }



    private void handleInput(float delta) {
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
            isAttacking = true;
            animationTime = 0f;
        }

        if (Gdx.input.isKeyJustPressed(rollKey) && !isRolling && !isJumping) {
            isRolling = true;
            animationTime = 0f;
            rollTimer = 0f;
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
        batch.draw(currentFrame, position.x, position.y,renderWidth, renderHeight);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
        animation.getDeathCase().getKeyFrames()[0].getTexture().dispose();
    }

    public void reset() {
        position.set(initialPosition);
        health = initialHealth;
        attackDamage = initialAttack;
        isDead = false;
        isAttacking = false;
        hasHitEnemy = false;
        velocity.set(0, 0);
        animationTime = 0f;
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


    @Override
    public void update(float delta) {

    }
}
