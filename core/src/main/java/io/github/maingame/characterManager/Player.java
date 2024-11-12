package io.github.maingame.characterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private boolean hasHitEnemy = false;
    private boolean isDead = false;

    public Player(Vector2 position, List<Platform> platforms) {
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png",
            "_Attack.png","_Death.png", 120, 80, 0.1f),
            300,100, 25);
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
                attacking = false;
            animationTime = 0f;
        }

        if (isDead) {
            animationTime += delta;
        } else {
            handleInput(delta);
            animationTime += delta;

            if (attacking) {
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
                        jumping = false;
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
        if (isDead) return;

        this.health -= damage - armor;

        if (this.health <= 0) {
            isDead = true;
            animationTime = 0f;
        }
    }



    private void handleInput(float delta) {
        if (attacking || isDead) return;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
            lookingRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
            lookingRight = true;
        } else {
            idle();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !jumping) {
            velocity.y = jumpVelocity;
            jumping = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !attacking && !jumping) {
            attacking = true;
            animationTime = 0f;
        }
    }

    @Override
    public TextureRegion getCurrentFrame() {
        if (isDead) {
            return animation.getDeathCase().getKeyFrame(animationTime, false);
        }
        return super.getCurrentFrame();
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
        attacking = false;
        hasHitEnemy = false;
        velocity.set(0, 0);
        animationTime = 0f;
    }

    @Override
    public void update(float delta) {

    }
}
