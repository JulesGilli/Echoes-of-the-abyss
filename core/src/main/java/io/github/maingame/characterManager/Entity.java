package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.utilsManager.lifeCycle;
import io.github.maingame.design2dManager.AnimationManager;

public abstract class Entity implements lifeCycle{
    protected Vector2 position;
    protected Vector2 velocity;
    protected AnimationManager animation;
    protected boolean isAttacking = false;
    protected boolean isJumping = false;
    protected boolean isLookingRight;
    protected int SIZE;
    protected float SPEED;
    protected float JUMP_VELOCITY;
    protected float GRAVITY;
    protected float animationTime;
    protected int gold;
    protected int health;
    protected int maxHealth;
    protected int attack;
    protected int armor = 0;
    protected int attackIncrease = 0;
    protected float speedIncrease = 0;

    public Entity(Vector2 position, AnimationManager animation, int health, int gold) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.animation = animation;
        this.health = health;
        this.maxHealth = health;
        this.gold = gold;
    }

    public void flipAnimation(TextureRegion currentFrame) {
        if (!isLookingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }   if (isLookingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
    }

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

    public  int getMaxHealth() {
        return maxHealth;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getAttack() {
        return attack + attackIncrease;
    }

    public void receiveDamage(int damage){
        this.health -= damage - armor;
    }

    public float getSpeed(float SPEED) {
        return SPEED + speedIncrease;
    }

    public void setAttackIncrease(int attackIncrease) {
        this.attackIncrease = attackIncrease;
    }

    public void setSpeedIncrease(float speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }



}

