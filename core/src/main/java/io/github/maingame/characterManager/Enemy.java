package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;
import java.lang.Math.*;

public class Enemy extends Entity {
    private Player target;
    private float range;
    private float attackCooldown = 1.5f;
    private float timeSinceLastAttack = 0f;
    private boolean isDead = false;
    private boolean isDying = false;

    private boolean hasHitPlayer = false;


    public Enemy(Vector2 position, List<Platform> platforms, Player player) {
        super(position, new AnimationManager("_RunEnemy.png","_IdleEnemy.png","_Jump.png","_AttackEnemy.png","_DeathEnemy.png","_Roll.png", 120, 80, 0.1f,0.1f), 50, 10, 10);
        this.target = player;
        this.speed = 300;
        this.jumpVelocity = 1200;
        this.gravity = -25;
        this.platforms = platforms;
        this.range = 200;
        this.renderWidth = 450;
        this.renderHeight = 300;
        this.attackRange = 200;

    }

    public boolean inRange() {
        float distance = Math.abs(target.getPosition().x - position.x);
        return distance <= range;
    }

    public void makeAction(){
        if (isDead || attacking) return;

        if (inRange()) {
            if (timeSinceLastAttack >= attackCooldown) {
                System.out.println("Enemy in range of player.");

                attack();
                timeSinceLastAttack = 0f;

                if (!hasHitPlayer && isCollidingWith(target, attackRange)) {
                    System.out.println("Enemy hit player!");

                    target.receiveDamage(attackDamage);
                    hasHitPlayer = true;
                } else {
                    System.out.println("Enemy attack missed or already hit.");
                }
            } else {
                idle();
            }
        } else {
            walk();
        }
    }

    public void idle() {
        velocity.x = 0;
    }

    @Override
    protected void checkAttackFinish() {
        if (animation.getAttackCase().isAnimationFinished(animationTime)) {
            attacking = false;
            hasHitPlayer = false;
            animationTime = 0f;
        }
    }


    public void walk(){
        if (position.x > target.position.x){
            moveLaterally(-speed);
        }
        else {
            moveLaterally(speed);
        }
    }

    @Override
    public void receiveDamage(float damage) {
        if (!isDying) {
            health -= damage;
            System.out.println("Enemy prend " + damage + " damage, vie restante : " + health);

            if (health <= 0) {
                isDying = true;
                animationTime = 0f;
            }
        }
    }



    @Override
    public void update(float delta) {
        timeSinceLastAttack += delta;

        if (isDying) {
            animationTime += delta;
            if (animation.getDeathCase().isAnimationFinished(animationTime)) {
                isDead = true;
            }
        } else {
            makeAction();
            animationTime += delta;

            if (attacking) {
                checkAttackFinish();
            } else {
                applyGravity();
                checkOnPlatform();
                position.add(velocity.cpy().scl(delta));
                checkOnFloor();
            }
        }
    }

    public boolean isDeathAnimationFinished() {
        return isDead;
    }

    @Override
    public TextureRegion getCurrentFrame() {
        if (isDying) {
            return flipAnimationCheck(animation.getDeathCase().getKeyFrame(animationTime, false));
        } else if (attacking) {
            return flipAnimationCheck(animation.getAttackCase().getKeyFrame(animationTime, true));
        } else if (jumping) {
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
        batch.draw(currentFrame, position.x, position.y, 450, 300);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
        animation.getDeathCase().getKeyFrames()[0].getTexture().dispose();
    }
}
