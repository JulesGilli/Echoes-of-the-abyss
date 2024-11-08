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
    private final List<Platform> platforms;
    public static final int RENDER_WIDTH = 300;
    public static final int RENDER_HEIGHT = 200;

    public Player(Vector2 position, List<Platform> platforms) {
        super(position, new AnimationManager("_Run.png","_Idle.png","_Jump.png","_Attack.png"),100,0);
        this.SIZE = 50;
        this.SPEED = 350;
        this.JUMP_VELOCITY = 1200;
        this.GRAVITY = -50;
        this.platforms = platforms;
    }

    @Override
    public void update(float delta) {
        Input(delta);
        animationTime += delta;

        if (isJumping) {
            velocity.y += GRAVITY;

        }


        for (Platform platform : platforms) {
            if (isOnPlatform(platform) && velocity.y <= 0) {
                position.y = platform.getBounds().y + platform.getBounds().height;
                velocity.y = 0;
                isJumping = false;
                break;
            }
        }



        position.add(velocity.cpy().scl(delta));

        if (position.y <= 0) {
            position.y = 0;
            isJumping = false;
        }
    }

    private boolean isOnPlatform(Platform platform) {
        return position.y <= platform.getBounds().y + platform.getBounds().height &&
            position.y >= platform.getBounds().y &&
            position.x + SIZE > platform.getBounds().x &&
            position.x < platform.getBounds().x + platform.getBounds().width;
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
            velocity.y = JUMP_VELOCITY;
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
