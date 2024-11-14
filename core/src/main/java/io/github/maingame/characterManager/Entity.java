package io.github.maingame.characterManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.itemManager.Inventory;
import io.github.maingame.utilsManager.lifeCycle;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;

public abstract class Entity implements lifeCycle{
    protected boolean isWalking = false;
    protected boolean isJumping = false;
    protected boolean isAttacking = false;
    protected List<Platform> platforms;
    protected Vector2 position;
    protected Vector2 velocity;
    protected AnimationManager animation;
    protected boolean isLookingRight;
    protected float speed;
    protected float jumpVelocity;
    protected float gravity;
    protected float animationTime;
    protected int gold;
    protected float health;
    protected float maxHealth;
    protected float attackDamage;
    protected int armor = 0;
    protected float attackBonus  = 0;
    protected float speedBonus  = 0;
    protected int renderWidth  = 100;
    protected int renderHeight  = 100;
    protected float attackRange;
    protected Vector2 initialPosition;
    protected float initialHealth;
    protected int initialGold;
    protected float initialAttack;
    protected boolean isDead = false;
    protected Inventory inventory;

    public Entity(Vector2 position, AnimationManager animation, float health, int gold, float attack) {
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
        if (isJumping) {
            velocity.y += gravity;
        }
    }

    protected void checkAttackFinish(){
        if (animation.getAttackCase().isAnimationFinished(animationTime)) {
            isAttacking = false;
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
        isJumping = false;
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
            isJumping = false;
        }
    }



    public TextureRegion flipAnimationCheck(TextureRegion currentFrame) {
        if ((isLookingRight && currentFrame.isFlipX()) || (!isLookingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }
        return currentFrame;
    }

    public TextureRegion getCurrentFrame() {
        if (isAttacking) {
            return flipAnimationCheck(animation.getAttackCase().getKeyFrame(animationTime, true));
        } else if (isJumping) {
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
        if (!isJumping) {
            velocity.y = jumpVelocity;
        }
        isJumping =true;
    }

    public void attack(){
        isAttacking = true;
        animationTime = 0f;
    }

    public void moveLaterally(float SPEED){
        velocity.x = SPEED;
        isLookingRight = !(SPEED < 0);
        isWalking = true;
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

    public float getHealth() {
        return health;
    }

    public  float getMaxHealth() {
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

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void receiveDamage(float damage){
        this.health -= damage - armor;
    }

    public float getSpeed() {
        return speed + speedBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public void setSpeedBonus(float speedBonus) {
        this.speedBonus = speedBonus;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
        animation.getDeathCase().getKeyFrames()[0].getTexture().dispose();
    }

}

