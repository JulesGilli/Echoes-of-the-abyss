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
    private Animation<TextureRegion> deathCase;

    public AnimationManager(String walkAsset, String idleAsset, String jumpAsset, String attackAsset, String deathAsset, int frameWidth, int frameHeight, float frameDuration) {
        Texture walkSteps = new Texture(Gdx.files.internal(walkAsset));
        Texture idleSteps= new Texture(Gdx.files.internal(idleAsset));
        Texture jumpSteps = new Texture(Gdx.files.internal(jumpAsset));
        Texture attackSteps = new Texture(Gdx.files.internal(attackAsset));
        Texture deathSteps = new Texture(Gdx.files.internal(deathAsset));

        walkCase= createAnimation(walkSteps, frameWidth, frameHeight, frameDuration);
        idleCase = createAnimation(idleSteps, frameWidth, frameHeight, frameDuration);
        jumpCase = createAnimation(jumpSteps, frameWidth, frameHeight, frameDuration);
        attackCase = createAnimation(attackSteps, frameWidth, frameHeight, frameDuration);
        deathCase = createAnimation(deathSteps, frameWidth, frameHeight, frameDuration);  // Initialisation de l'animation de mort

    }

    private Animation<TextureRegion> createAnimation(Texture allSteps, int stepWidth, int stepHeight, float stepDuration) {
        TextureRegion[][] tmpSteps = TextureRegion.split(allSteps, stepWidth, stepHeight);
        int totalFrames = tmpSteps.length * tmpSteps[0].length;
        TextureRegion[] frames = new TextureRegion[totalFrames];
        int indexFrame = 0;
        for (TextureRegion[] tmpStep : tmpSteps) {
            for (TextureRegion textureRegion : tmpStep) {
                frames[indexFrame++] = textureRegion;
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

    public Animation<TextureRegion> getDeathCase() {
        return deathCase;
    }
}
