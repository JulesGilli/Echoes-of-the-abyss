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
    public Enemy(Vector2 position, List<Platform> platforms) {
        super(position, new AnimationManager("SkeletonIdle.png", "SkeletonIdle.png", "SkeletonIdle.png", "SkeletonIdle.png", 150, 101, 0.2f), 50, 10);
    }

    @Override
    public void update(float delta) {
        animationTime += delta;
        move();
        position.add(velocity.scl(delta));
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getIdleCase().getKeyFrame(animationTime, true);
        batch.draw(currentFrame, position.x, position.y, 400, 350);
    }

    private void move() {
    }

    public void attack(Player player) {
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
    }
}
