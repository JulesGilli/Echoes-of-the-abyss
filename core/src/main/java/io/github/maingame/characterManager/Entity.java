package io.github.maingame.characterManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.utilsManager.lifeCycle;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;

public abstract class Entity implements lifeCycle{
    protected boolean walking = false;
    protected boolean jumping = false;
    protected boolean attacking = false;
    protected List<Platform> platforms;
    protected Vector2 position;
    protected Vector2 velocity;
    protected AnimationManager animation;
    protected boolean lookingRight ;
    protected float speed;
    protected float jumpVelocity;
    protected float gravity;
    protected float animationTime;
    protected int gold;
    protected int health;
    protected int maxHealth;
    protected float attackDamage;
    protected int armor = 0;
    protected float attackBonus  = 0;
    protected float speedBonus  = 0;
    protected int renderWidth  = 100;
    protected int renderHeight  = 100;
    protected float attackRange;

    public Entity(Vector2 position, AnimationManager animation, int health, int gold, float attack) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.animation = animation;
        this.health = health;
        this.maxHealth = health;
        this.gold = gold;
        this.attackDamage = attack;
    }

    protected Vector2 getCenterPosition()
    {
        return new Vector2((position.x + (float) renderWidth / 2), (position.y + (float) renderHeight / 2));
    }

    public void applyGravity(){
        if (jumping) {
            velocity.y += gravity;
        }
    }

    protected void checkAttackFinish(){
        if (animation.getAttackCase().isAnimationFinished(animationTime)) {
            attacking = false;
            animationTime = 0f;
        }
    }

    protected boolean isOnPlatform(Platform platform) {
        return position.y <= platform.getBounds().y + platform.getBounds().height &&
                position.y >= platform.getBounds().y &&
                position.x + renderWidth > platform.getBounds().x &&
                position.x < platform.getBounds().x + platform.getBounds().width;
    }

    protected void applyPlatformPosition(Platform platform){
        position.y = platform.getBounds().y + platform.getBounds().height;
        velocity.y = 0;
        jumping = false;
    }

    protected void checkOnPlatform(){
        for (Platform platform : platforms) {
            if (isOnPlatform(platform) && velocity.y <= 0) {
                applyPlatformPosition(platform);
                break;
            }
        }
    }

    protected void checkOnFloor(){
        if (position.y <= 0) {
            position.y = 0;
            jumping = false;
        }
    }



    public TextureRegion flipAnimationCheck(TextureRegion currentFrame) {
        if (!lookingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        if (lookingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        return currentFrame;
    }

    public TextureRegion getCurrentFrame() {
        if (attacking) {
            return flipAnimationCheck(animation.getAttackCase().getKeyFrame(animationTime, true));
        } else if (jumping) {
            return flipAnimationCheck(animation.getJumpCase().getKeyFrame(animationTime, true));
        } else if (velocity.x != 0) {
            return flipAnimationCheck(animation.getWalkCase().getKeyFrame(animationTime, true));
        } else {
            return flipAnimationCheck(animation.getIdleCase().getKeyFrame(animationTime, true));
        }
    }

    public void idle(){
        velocity.x = 0;
    }

    public void jump(){
        if (!jumping) {
            velocity.y = jumpVelocity;
        }
        jumping=true;
    }

    public void attack(){
        attacking = true;
        animationTime = 0f;
    }

    public void moveLaterally(float SPEED){
        velocity.x = SPEED;
        lookingRight = !(SPEED < 0);
        walking = true;
    }

    public boolean isCollidingWith(Entity other, float attackRange) {
        Vector2 thisCenter = getCenterPosition();
        Vector2 otherCenter = other.getCenterPosition();

        float distance = thisCenter.dst(otherCenter);

        System.out.println(distance <= attackRange);
        return distance <= attackRange;
    }

    public boolean isAlive() {
        return health > 0;
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

    public float getAttack() {
        return attackDamage + attackBonus;
    }

    public void receiveDamage(float damage){
        this.health -= damage - armor;
    }

    public float getSpeed(float SPEED) {
        return speed + speedBonus;
    }

    public void setAttackBonus(int attackIncrease) {
        this.attackBonus = attackBonus;
    }

    public void setSpeedBonus(float speedIncrease) {
        this.speedBonus = speedBonus;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }



}

