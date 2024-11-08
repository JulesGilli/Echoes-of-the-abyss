package io.github.maingame.design2dManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;

public class AnimationManager {
    private Animation<TextureRegion> walkCase;
    private Animation<TextureRegion> idleCase;
    private Animation<TextureRegion> jumpCase;
    private Animation<TextureRegion> attackCase;
    private float timeOfCase = 0f;

    public AnimationManager(String walkAsset, String idleAsset, String jumpAsset, String attachAsset) {
        Texture walkSteps = new Texture(Gdx.files.internal(walkAsset));
        Texture idleSteps= new Texture(Gdx.files.internal(idleAsset));
        Texture jumpSteps = new Texture(Gdx.files.internal(jumpAsset));
        Texture attackSteps = new Texture(Gdx.files.internal(attachAsset));
        walkCase= createAnimation(walkSteps, 120, 80, 0.15f);
        idleCase = createAnimation(idleSteps, 120, 80, 0.1f);
        jumpCase = createAnimation(jumpSteps, 120, 80, 0.15f);
        attackCase = createAnimation(attackSteps, 120, 80, 0.1f);
    }

    private Animation<TextureRegion> createAnimation(Texture allSteps, int stepWidth, int stepHeight, float stepDuration) {
        TextureRegion[][] tmpSteps = TextureRegion.split(allSteps, stepWidth, stepHeight);
        int totalFrames = tmpSteps.length * tmpSteps[0].length;
        TextureRegion[] frames = new TextureRegion[totalFrames];
        int indexFrame = 0;
        for (int i = 0; i < tmpSteps.length; i++) {
            for (int j = 0; j < tmpSteps[i].length; j++) {
                frames[indexFrame++] = tmpSteps[i][j];
            }
        }
        return new Animation<>(stepDuration, frames);
    }

    public Animation<TextureRegion> getAttackCase() {
        return attackCase;
    }

    public Animation<TextureRegion> getIdleCase() {
        return idleCase;
    }

    public Animation<TextureRegion> getJumpCase() {
        return jumpCase;
    }

    public Animation<TextureRegion> getWalkCase() {
        return walkCase;
    }
}
