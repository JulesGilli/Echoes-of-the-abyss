package io.github.maingame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.core.GameStat;
import io.github.maingame.utils.AnimationManager;
import io.github.maingame.utils.DamageText;
import io.github.maingame.utils.GoldText;
import io.github.maingame.utils.Platform;
import io.github.maingame.utils.TextureManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Enemy extends Entity {
    protected final List<DamageText> damageTexts = new ArrayList<>();
    protected final List<GoldText> goldTexts = new ArrayList<>();
    protected Player target;
    protected float range;
    protected float attackCooldown = 1.5f;
    protected float timeSinceLastAttack = 0f;
    protected float attackDelay = 0.3f;
    protected float attackDelayTimer = 0f;
    protected float baseAttackDamage;
    protected float baseSpeed;
    protected boolean isDead = false;
    protected boolean isDying = false;
    protected boolean hasHitPlayer = false;
    protected float invulnerabilityDuration = 0.5f;
    protected float invulnerabilityTimer = 0f;
    protected boolean isElite = false;
    protected Color eliteTint = new Color(1f, 0.6f, 0.6f, 1f);
    private boolean deathEffectsTriggered = false;

    public Enemy(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat, AnimationManager animation, float health, float speed, float attackDamage, float range, float attackCooldown) {
        super(position, animation, health, 10, attackDamage);
        this.target = player;
        this.platforms = platforms;
        this.speed = speed;
        this.range = range;
        this.attackCooldown = attackCooldown;

        this.baseAttackDamage = attackDamage;
        this.baseSpeed = speed;
    }

    public void updateStats(GameStat stat) {
        int level = stat.getFloors();
        float multiplier = (float) Math.pow(1.3, level - 1);

        this.maxHealth = this.maxHealth * multiplier;
        this.health = this.maxHealth;

        this.attackDamage = this.baseAttackDamage * multiplier;

        this.attackCooldown = this.attackCooldown / multiplier;

        this.gold = Math.round(this.gold * multiplier);

        this.speed = this.baseSpeed * (1 + (level - 1) * 0.1f);

        float animationSpeedMultiplier = (float) Math.pow(0.95, level - 1);
        animation.updateFrameDurations(animationSpeedMultiplier);
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
            flashTimer = 0.1f;

            damageTexts.add(new DamageText("-" + (int) damage, new Vector2(position.x, position.y)));

            if (health <= 0) {
                isDying = true;
                animationTime = 0f;
                goldTexts.add(new GoldText("" + this.gold, new Vector2(position.x, position.y)));
            } else {
                isHit = true;
                hitAnimationTime = 0f;
                invulnerabilityTimer = invulnerabilityDuration;
            }
        }
    }

    public void update(float delta) {
        timeSinceLastAttack += delta;
        updateFlash(delta);

        if (isDying) {
            animationTime += delta;
            if (animation.getDeathCase().isAnimationFinished(animationTime)) {
                isDead = true;
            }
            return;
        }

        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer -= delta;
        }

        if (isHit) {
            hitAnimationTime += delta;
            if (hitAnimationTime >= hitDuration) {
                isHit = false;
                hitAnimationTime = 0f;
            }
            return;
        }

        makeAction(delta);

        if (!isHit) {
            applyGravity();
            checkOnPlatform();
            position.add(velocity.cpy().scl(delta));
            checkOnFloor();
            position.x = com.badlogic.gdx.math.MathUtils.clamp(position.x, -300, 3200 - renderWidth);
        }

        animationTime += delta;

        if (isAttacking) {
            checkAttackFinish();
        }

    }

    public abstract void makeAction(float delta);

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();

        if (isElite) {
            batch.setColor(eliteTint);
        }
        batch.draw(
            currentFrame,
            position.x,
            position.y,
            renderWidth,
            renderHeight
        );
        if (isElite) {
            batch.setColor(1f, 1f, 1f, 1f);
        }

        if (flashTimer > 0) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.setColor(1f, 1f, 1f, flashTimer / 0.1f);
            batch.draw(currentFrame, position.x, position.y, renderWidth, renderHeight);
            batch.setColor(1f, 1f, 1f, 1f);
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }

        if (!isDead && !isDying) {
            renderHealthBar(batch);
        }

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
        if (isHit) {
            return flipAnimationCheck(animation.getHitCase().getKeyFrame(hitAnimationTime, false));
        } else if (isDying) {
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
        if (isHit) return;

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

    public boolean shouldTriggerDeathEffects() {
        if (isDying && !deathEffectsTriggered) {
            deathEffectsTriggered = true;
            return true;
        }
        return false;
    }

    public void setScaleFactor(float scaleFactor) {
        this.renderWidth = (int) (this.animation.getIdleCase().getKeyFrames()[0].getRegionWidth() * scaleFactor);
        this.renderHeight = (int) (this.animation.getIdleCase().getKeyFrames()[0].getRegionHeight() * scaleFactor);
    }

    private void renderHealthBar(SpriteBatch batch) {
        float healthPercent = Math.max(0, Math.min(1f, health / maxHealth));
        if (healthPercent >= 1f) return;

        Texture pixel = TextureManager.getWhitePixel();
        float barWidth = renderWidth * 0.5f;
        float barHeight = 8f;
        float barX = position.x + renderWidth * 0.25f;
        float barY = position.y + renderHeight - 10f;

        batch.setColor(0.2f, 0.2f, 0.2f, 0.8f);
        batch.draw(pixel, barX - 1, barY - 1, barWidth + 2, barHeight + 2);

        float r = 1f - healthPercent;
        float g = healthPercent;
        batch.setColor(r, g, 0.1f, 0.9f);
        batch.draw(pixel, barX, barY, barWidth * healthPercent, barHeight);

        if (isElite) {
            batch.setColor(1f, 0.2f, 0.2f, 0.8f);
            batch.draw(pixel, barX - 2, barY - 2, barWidth + 4, 2);
            batch.draw(pixel, barX - 2, barY + barHeight, barWidth + 4, 2);
            batch.draw(pixel, barX - 2, barY - 2, 2, barHeight + 4);
            batch.draw(pixel, barX + barWidth, barY - 2, 2, barHeight + 4);
        }

        batch.setColor(1f, 1f, 1f, 1f);
    }

    public boolean isDying() {
        return isDying;
    }

    public void setElite(boolean elite) {
        this.isElite = elite;
        if (elite) {
            this.health *= 2f;
            this.maxHealth *= 2f;
            this.attackDamage *= 1.5f;
            this.gold *= 2;
        }
    }

    public boolean isElite() {
        return isElite;
    }
}
