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

public abstract class Enemy extends Entity {
    protected Player target;
    protected float range;
    protected float attackCooldown = 1.5f;
    protected float timeSinceLastAttack = 0f;
    protected float attackDelay = 0.3f;
    protected float attackDelayTimer = 0f;

    protected boolean isDead = false;
    protected boolean isDying = false;
    protected boolean hasHitPlayer = false;

    protected float invulnerabilityDuration = 0.5f;
    protected float invulnerabilityTimer = 0f;

    protected final List<DamageText> damageTexts = new ArrayList<>();
    protected final List<GoldText> goldTexts = new ArrayList<>();

    public Enemy(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat, AnimationManager animation, float health, float speed, float attackDamage, float range, float attackCooldown) {
        super(position, animation, health, 10, attackDamage);
        this.target = player;
        this.platforms = platforms;
        this.speed = speed;
        this.range = range;
        this.attackCooldown = attackCooldown;
    }

    public void updateStats(GameStat stat) {
        float waveMultiplier = 1 + (stat.getFloors() * 0.5f);
        this.health = this.maxHealth * waveMultiplier;
        this.attackDamage *= waveMultiplier;
    }


    protected boolean isPlayerInFront() {
        return (isLookingRight && target.getPosition().x > position.x) ||
            (!isLookingRight && target.getPosition().x < position.x);
    }

    public boolean inRange() {
        float distance = Math.abs(target.getPosition().x - position.x);
        return distance <= range && isPlayerInFront();
    }

    public void walk() {
        if (target.getPosition().x > position.x) {
            moveLaterally(speed);
        } else {
            moveLaterally(-speed);
        }
    }

    @Override
    public void receiveDamage(float damage) {
        if (!isDying && invulnerabilityTimer <= 0) {
            health -= damage;

            damageTexts.add(new DamageText("-" + (int) damage, new Vector2(position.x, position.y)));

            if (health <= 0) {
                isDying = true;
                animationTime = 0f;
                goldTexts.add(new GoldText("+10", new Vector2(position.x, position.y)));
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

    public abstract void makeAction(float delta);

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(
            currentFrame,
            position.x,
            position.y,
            renderWidth,
            renderHeight
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

        for (Iterator<GoldText> iterator = goldTexts.iterator(); iterator.hasNext(); ) {
            GoldText text = iterator.next();
            text.render(batch);
            text.update(Gdx.graphics.getDeltaTime());
            if (text.isExpired()) {
                text.dispose();
                iterator.remove();
            }
        }
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
    protected void checkAttackFinish() {
        if (animation.getAttackCase().isAnimationFinished(animationTime)) {
            isAttacking = false;
            animationTime = 0f;

            if (!hasHitPlayer && inRange()) {
                target.receiveDamage(attackDamage);
                hasHitPlayer = true;
            }
        }
    }

    public boolean isDeathAnimationFinished() {
        return isDead;
    }

    public void setScaleFactor(float scaleFactor) {
        this.renderWidth = (int) (this.animation.getIdleCase().getKeyFrames()[0].getRegionWidth() * scaleFactor);
        this.renderHeight = (int) (this.animation.getIdleCase().getKeyFrames()[0].getRegionHeight() * scaleFactor);
    }

}
