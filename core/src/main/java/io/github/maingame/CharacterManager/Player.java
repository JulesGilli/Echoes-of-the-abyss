package io.github.maingame.CharacterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.design2dManager.AnimationManager;

public class Player extends character {
    private int health;
    private int gold;
    public static final int RENDER_WIDTH = 300;
    public static final int RENDER_HEIGHT = 200;

    public Player(Vector2 position) {
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png","_Attack.png"));
        this.health = 100;
        this.gold = 0;
        this.SIZE = 50;
        this.SPEED = 350;
        this.JUMP_VELOCITY = 1000;
        this.GRAVITY = -50;
    }

    @Override
    public void update(float delta) {
        Input(delta);
        animationTime += delta;
        if (isJumping) {
            velocity.y += GRAVITY;
        }
        position.add(velocity.cpy().scl(delta));
        if (position.y <= 0) {
            position.y = 0;
            isJumping = false;
        }
    }

    private void Input(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -SPEED;
            isLookingRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = SPEED;
            isLookingRight = true;
        } else {
            velocity.x = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocity.y =  JUMP_VELOCITY;
            isJumping = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        if (isJumping) {
            currentFrame = animation.getJumpCase().getKeyFrame(animationTime, true);
        } else if (velocity.x != 0) {
            currentFrame = animation.getWalkCase().getKeyFrame(animationTime, true);
        } else {
            currentFrame = animation.getIdleCase().getKeyFrame(animationTime, true);
        }
        flipAnimation(currentFrame);
        batch.draw(currentFrame, position.x, position.y,RENDER_WIDTH, RENDER_HEIGHT);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
    }
}
