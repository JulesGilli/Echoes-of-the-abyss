package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;

public class Enemy extends Entity {
    private int health;
    private int damage;
    private Player target;
    private float range;
    public Enemy(Vector2 position, List<Platform> platforms, Player player) {
        super(position, new AnimationManager("SkeletonIdle.png", "SkeletonIdle.png", "SkeletonIdle.png", "SkeletonIdle.png", 150, 101, 0.2f), 50, 10);
        this.target = player;
        this.SPEED = 1000;
        this.JUMP_VELOCITY = 1200;
        this.GRAVITY = -25;
        this.platforms = platforms;
        this.range = 1.0f;
    }

    public void update(float delta) {
        moveToPlayer(target);
        animationTime += delta;
        if (isJumping){
            applyGravity();
        }
        checkOnPlatform();
        position.add(velocity.scl(delta));
        checkOnFloor();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y, 400, 350);
    }

    private void moveToPlayer(Entity target) {
        Vector2 playerCenter = target.getCenterPosition();
        Vector2 enemyCenter = getCenterPosition();
        Vector2 direction = new Vector2(playerCenter.x - enemyCenter.x, playerCenter.y - enemyCenter.y);
        direction.nor();
        lateralMove(direction.x * SPEED);
        System.out.println(velocity.x);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
    }
}
