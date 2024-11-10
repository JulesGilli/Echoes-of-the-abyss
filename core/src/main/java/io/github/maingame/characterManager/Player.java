package io.github.maingame.characterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;

import java.util.List;

public class Player extends Entity {

    public Player(Vector2 position, List<Platform> platforms) {
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png","_Attack.png", 120, 80, 0.1f),100,0);
        this.SPEED = 500;
        this.JUMP_VELOCITY = 1000;
        this.GRAVITY = -50;
        this.platforms = platforms;
        this.RENDER_WIDTH = 450;
        this.RENDER_HEIGHT = 300;
    }

    @Override
    public void update(float delta) {
        Input(delta);
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


    private void Input(float delta) {
        if (isAttacking) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -SPEED;
            isLookingRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = SPEED;
            isLookingRight = true;
        } else {
            idle();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocity.y = JUMP_VELOCITY;
            isJumping = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !isAttacking && !isJumping) {
            isAttacking = true;
            animationTime = 0f;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y,RENDER_WIDTH, RENDER_HEIGHT);
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
    }
}
