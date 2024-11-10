package io.github.maingame.characterManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
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
    protected int RENDER_WIDTH;
    protected int RENDER_HEIGHT;

    public Entity(Vector2 position, AnimationManager animation, int health, int gold) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.animation = animation;
        this.health = health;
        this.maxHealth = health;
        this.gold = gold;
    }

    protected Vector2 getCenterPosition()
    {
        return new Vector2((position.x + (float) RENDER_WIDTH / 2), (position.y + (float) RENDER_HEIGHT / 2));
    }

    public void applyGravity(){
        if (isJumping) {
            velocity.y += GRAVITY;
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
                position.x + RENDER_WIDTH > platform.getBounds().x &&
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
        if (!isLookingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }   if (isLookingRight && currentFrame.isFlipX()) {
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
            velocity.y = JUMP_VELOCITY;
        }
        isJumping=true;
    }

    public void attack(){
        isAttacking = true;
        animationTime = 0f;
        System.out.println("ATTACK");
    }

    public void lateralMove(float SPEED){
        velocity.x = SPEED;
        isLookingRight = !(SPEED < 0);
        isWalking = true;
    }

    public boolean isCollidingWith(Entity other) {
        return position.x < other.position.x + other.RENDER_WIDTH &&
            position.x + RENDER_WIDTH > other.position.x &&
            position.y < other.position.y + other.RENDER_HEIGHT &&
            position.y + RENDER_HEIGHT > other.position.y;
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

