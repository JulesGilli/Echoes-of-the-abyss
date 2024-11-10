package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;
import java.lang.Math.*;

public class Enemy extends Entity {
    private int health;
    private int damage;
    private Player target;
    private float range;
    public Enemy(Vector2 position, List<Platform> platforms, Player player) {
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png","_Attack.png", 120, 80, 0.1f), 50, 10);
        this.target = player;
        this.SPEED = 300;
        this.JUMP_VELOCITY = 1200;
        this.GRAVITY = -25;
        this.platforms = platforms;
        this.range = 400;
    }

    public void walk(){
        if (inRange())
        {
            idle();
        }
        else if (position.x > target.position.x){
            lateralMove(-SPEED);
        }
        else {
            lateralMove(SPEED);
        }
    }

    public boolean inRange(){
        float distance = target.position.dst(position);
        return distance <= range;
    }

    public void makeAction(){
        if (isAttacking){
            return;
        }

        else if (inRange()){
            attack();
        }
        else{
            idle();
        }
    }

    @Override
    public void update(float delta) {
        makeAction();
        animationTime += delta;
        if (isAttacking) {
            checkAttackFinish();
        }
        else {
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


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y, 400, 350);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
    }
}
