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
        super(position, new AnimationManager("_RunEnemy.png","_Idle.png","_Jump.png","_AttackEnemy.png", 120, 80, 0.1f), 50, 10);
        this.target = player;
        this.SPEED = 300;
        this.JUMP_VELOCITY = 1200;
        this.GRAVITY = -25;
        this.platforms = platforms;
        this.range = 200;
    }

    public boolean inRange() {
        float distance = Math.abs(target.getPosition().x - position.x);
        return distance <= range;
    }

    public void makeAction(){
        if (isAttacking){
            return;
        }

        if (inRange()){
            attack();
        }
        else{
            walk();
        }
    }

    public void walk(){
        if (position.x > target.position.x){
            lateralMove(-SPEED);
        }
        else {
            lateralMove(SPEED);
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
            checkOnPlatform();
            position.add(velocity.cpy().scl(delta));
            checkOnFloor();
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
    }
}
