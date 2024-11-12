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
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png","_Attack.png","_Death.png", 120, 80, 0.1f),300,100, 25);
        this.SPEED = 500;
        this.JUMP_VELOCITY = 1000;
        this.GRAVITY = -25;
        this.platforms = platforms;
        this.RENDER_WIDTH = 450;
        this.RENDER_HEIGHT = 300;
        this.attackRange = 200;
    }

    public void update(float delta, List<Enemy> enemies) {
        if (health <= 0 && !isDead) {
            isDead = true;
            isAttacking = false;
            animationTime = 0f;
        }

        if (isDead) {
            if (animation.getDeathCase().isAnimationFinished(animationTime)) {
            }
            animationTime += delta;
        } else {

        Input(delta);
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
        }
        else {
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
        if (isDead) {
            return;
        }

        this.health -= damage - armor;

        if (this.health <= 0) {
            isDead = true;
            animationTime = 0f;
        }
    }



    private void Input(float delta) {
        if (isAttacking || isDead) return;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -SPEED;
            isLookingRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = SPEED;
            isLookingRight = true;
        } else {
            idle();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocity.y = JUMP_VELOCITY;
            isJumping = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !isAttacking && !isJumping) {
            isAttacking = true;
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
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y,RENDER_WIDTH, RENDER_HEIGHT);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
        animation.getDeathCase().getKeyFrames()[0].getTexture().dispose();
    }
}
