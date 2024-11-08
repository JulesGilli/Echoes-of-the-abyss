package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.utilsManager.lifeCycle;
import io.github.maingame.design2dManager.AnimationManager;

public abstract class character implements lifeCycle{
    protected Vector2 position;
    protected Vector2 velocity;
    protected AnimationManager animation;
    protected boolean isJumping = false;
    protected boolean isLookingRight;
    protected int SIZE;
    protected float SPEED;
    protected float JUMP_VELOCITY;
    protected float GRAVITY;
    protected float animationTime;

    public character(Vector2 position, AnimationManager animation) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.animation = animation;
    }

    public void flipAnimation(TextureRegion currentFrame) {
        if (!isLookingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }   if (isLookingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
    }

}
