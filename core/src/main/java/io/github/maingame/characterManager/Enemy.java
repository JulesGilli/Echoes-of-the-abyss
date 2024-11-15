package io.github.maingame.characterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.design2dManager.AnimationManager;
import io.github.maingame.design2dManager.DamageText;
import io.github.maingame.design2dManager.GoldText;
import io.github.maingame.utilsManager.GameStat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Enemy extends Entity {
    private Player target;

    private final List<DamageText> damageTexts = new ArrayList<>();
    private final List<GoldText> goldText = new ArrayList<>();

    private float range;
    private float attackCooldown = 1.5f;
    private float timeSinceLastAttack = 0f;
    private float attackDelay = 0.3f;
    private float attackDelayTimer = 0f;

    private boolean isDead = false;
    private boolean isDying = false;

    private boolean hasHitPlayer = false;

    private float invulnerabilityDuration = 0.5f;
    private float invulnerabilityTimer = 0f;


    public Enemy(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {

        super(
            position,
            new AnimationManager(
                "SkeletonWalk.png",
                "SkeletonIdle.png",
                "SkeletonWalk.png",
                "SkeletonAttack.png",
                "SkeletonDeath.png",
                "SkeletonWalk.png",
                150, 101,
                0.1f, 0.1f
            ),
            50 * (1 + (gameStat.getFloors() / 50f)),
            10,
            10 * (1 + (gameStat.getFloors() / 50f))
        );

        TextureRegion firstFrame = animation.getIdleCase().getKeyFrames()[0];
        this.renderWidth = firstFrame.getRegionWidth();
        this.renderHeight = firstFrame.getRegionHeight();

        this.target = player;
        this.speed = 300;
        this.jumpVelocity = 1200;
        this.gravity = -25;
        this.platforms = platforms;
        this.range = 200;
        this.attackRange = 200;

    }

    public boolean inRange() {
        float distance = Math.abs(target.getPosition().x - position.x);
        return distance <= range && isPlayerInFront();
    }

    private boolean isPlayerInFront() {
        return (isLookingRight && target.getPosition().x > position.x) ||
            (!isLookingRight && target.getPosition().x < position.x);
    }

    public void makeAction(float delta){
        if (isDead || isAttacking) return;

        if (inRange()) {
            attackDelayTimer += delta;
            if (attackDelayTimer >= attackDelay && timeSinceLastAttack >= attackCooldown) {
                attack();
                timeSinceLastAttack = 0f;
                attackDelayTimer = 0f;
                hasHitPlayer = false;
            } else {
                idle();
            }
        } else {
            attackDelayTimer = 0f;

            if (!isPlayerInFront()) {
                isLookingRight = target.getPosition().x > position.x;
            }
            walk();
        }
    }

    @Override
    protected void checkAttackFinish() {
        if (animation.getAttackCase().isAnimationFinished(animationTime)) {
            isAttacking = false;
            animationTime = 0f;

            if (!hasHitPlayer && isPlayerInAttackRange()) {
                target.receiveDamage(attackDamage);
                hasHitPlayer = true;
            }
        }
    }

    private boolean isPlayerInAttackRange() {
        float distance = Math.abs(target.getPosition().x - position.x);

        if (distance > attackRange) return false;

        boolean playerIsInFront = (isLookingRight && target.getPosition().x > position.x) ||
            (!isLookingRight && target.getPosition().x < position.x);
        return playerIsInFront;
    }


    public void walk(){
        if (position.x > target.position.x) {
            moveLaterally(-speed);
            isLookingRight = false;
        } else {
            moveLaterally(speed);
            isLookingRight = true;
        }
    }

    @Override
    public void receiveDamage(float damage) {
        if (!isDying && invulnerabilityTimer <= 0) {
            health -= damage;

            damageTexts.add(new DamageText("-" + (int)damage, new Vector2(position.x, position.y)));

            if (health <= 0) {
                isDying = true;
                animationTime = 0f;
                goldText.add(new GoldText("+10", new Vector2(position.x, position.y)));
            } else {
                invulnerabilityTimer = invulnerabilityDuration;
            }
        }

    }



    @Override
    public void update(float delta) {
        timeSinceLastAttack += delta;

        if (isDying) {
            animationTime += delta;
            if (animation.getDeathCase().isAnimationFinished(animationTime)) {
                isDead = true;
            }
        } else {
            if (invulnerabilityTimer > 0) {
                invulnerabilityTimer -= delta;
            }

            makeAction(delta);
            animationTime += delta;

            if (isAttacking) {
                checkAttackFinish();
            } else {
                applyGravity();
                checkOnPlatform();
                position.add(velocity.cpy().scl(delta));
                checkOnFloor();
            }
        }
    }

    public boolean isDeathAnimationFinished() {
        return isDead;
    }

    @Override
    public TextureRegion getCurrentFrame() {
        if (isDying) {
            return flipAnimationCheck(animation.getDeathCase().getKeyFrame(animationTime, false));
        } else if (isAttacking) {
            return flipAnimationCheck(animation.getAttackCase().getKeyFrame(animationTime, true));
        } else if (isJumping) {
            return flipAnimationCheck(animation.getJumpCase().getKeyFrame(animationTime, true));
        } else if (velocity.x != 0) {
            return flipAnimationCheck(animation.getWalkCase().getKeyFrame(animationTime, true));
        } else {
            return flipAnimationCheck(animation.getIdleCase().getKeyFrame(animationTime, true));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();

        float scaleFactor = 3;
        float aspectRatio = renderWidth / (float) renderHeight;

        batch.draw(
            currentFrame,
            position.x,
            position.y,
            renderWidth * scaleFactor,
            renderHeight * scaleFactor
        );

        for (Iterator<DamageText> iterator = damageTexts.iterator(); iterator.hasNext(); ) {
            DamageText damageText = iterator.next();
            damageText.render(batch);
            damageText.update(Gdx.graphics.getDeltaTime());
            if (damageText.isExpired()) {
                damageText.dispose();
                iterator.remove();
            }
        }

        for (Iterator<GoldText> iterator = goldText.iterator(); iterator.hasNext(); ) {
            GoldText text = iterator.next();
            text.render(batch);
            text.update(Gdx.graphics.getDeltaTime());
            if (text.isExpired()) {
                text.dispose();
                iterator.remove();
            }
        }
    }

    public void dispose(){
        animation.getIdleCase().getKeyFrames()[0].getTexture().dispose();
        animation.getAttackCase().getKeyFrames()[0].getTexture().dispose();
        animation.getJumpCase().getKeyFrames()[0].getTexture().dispose();
        animation.getWalkCase().getKeyFrames()[0].getTexture().dispose();
        animation.getDeathCase().getKeyFrames()[0].getTexture().dispose();
    }
}
