package io.github.maingame.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.core.GameStat;
import io.github.maingame.utils.AnimationManager;
import io.github.maingame.utils.Platform;
import io.github.maingame.utils.TextureManager;

import java.util.List;

public class BossEnemy extends Enemy {
    private boolean enraged = false;
    private float chargeTimer = 0f;
    private boolean isCharging = false;
    private float chargeSpeed = 600f;
    private float chargeCooldown = 4f;
    private float chargeCooldownTimer = 0f;
    private float slamCooldown = 6f;
    private float slamCooldownTimer = 3f;
    private boolean isSlamming = false;
    private float slamTimer = 0f;

    public BossEnemy(Vector2 position, List<Platform> platforms, Player player, GameStat gameStat) {
        super(
            position,
            platforms,
            player,
            gameStat,
            new AnimationManager(
                "atlas/skeleton/sprite_skeleton_walk.png",
                "atlas/skeleton/sprite_skeleton_idle.png",
                "atlas/skeleton/sprite_skeleton_walk.png",
                "atlas/skeleton/sprite_skeleton_attack.png",
                "atlas/skeleton/sprite_skeleton_death.png",
                "atlas/skeleton/sprite_skeleton_walk.png",
                "atlas/skeleton/sprite_skeleton_hit.png",
                150, 101, 0.12f, 0.1f
            ),
            200,
            200,
            40,
            300,
            2.0f
        );

        this.renderWidth = 750;
        this.renderHeight = 505;
        this.gold = 100;
    }

    private float getCenterX() {
        return position.x + renderWidth / 2f;
    }

    @Override
    public boolean inRange() {
        float centerX = getCenterX();
        float targetCenterX = target.getPosition().x + 225;
        float distance = Math.abs(targetCenterX - centerX);
        return distance <= range;
    }

    @Override
    protected boolean isPlayerInFront() {
        float centerX = getCenterX();
        float targetX = target.getPosition().x + 225;
        return (isLookingRight && targetX > centerX) ||
            (!isLookingRight && targetX < centerX);
    }

    @Override
    public void walk() {
        float targetCenterX = target.getPosition().x + 225;
        float centerX = getCenterX();
        if (targetCenterX > centerX) {
            moveLaterally(speed);
        } else {
            moveLaterally(-speed);
        }
    }

    @Override
    public void makeAction(float delta) {
        if (isDead || isAttacking) return;

        if (!enraged && health <= maxHealth * 0.5f) {
            enraged = true;
            speed *= 1.3f;
            attackDamage *= 1.5f;
            attackCooldown *= 0.7f;
            chargeCooldown *= 0.6f;
            slamCooldown *= 0.6f;
        }

        chargeCooldownTimer += delta;
        slamCooldownTimer += delta;

        if (isCharging) {
            chargeTimer += delta;
            float chargeDir = isLookingRight ? 1f : -1f;
            velocity.x = chargeDir * chargeSpeed;
            if (chargeTimer >= 0.6f) {
                isCharging = false;
                chargeTimer = 0f;
                velocity.x = 0;
                if (inRange()) {
                    target.receiveDamage(attackDamage * 1.5f);
                }
            }
            return;
        }

        if (isSlamming) {
            slamTimer += delta;
            if (slamTimer >= 0.3f && position.y > 0) {
                velocity.y = -1500;
            }
            if (position.y <= 0 && slamTimer >= 0.3f) {
                isSlamming = false;
                slamTimer = 0f;
                velocity.x = 0;
                velocity.y = 0;
                position.y = 0;
                isJumping = false;
                float centerX = getCenterX();
                float targetCenterX = target.getPosition().x + 225;
                float dist = Math.abs(targetCenterX - centerX);
                if (dist < 400) {
                    target.receiveDamage(attackDamage * 2f);
                }
            }
            return;
        }

        if (chargeCooldownTimer >= chargeCooldown && !inRange()) {
            isLookingRight = target.getPosition().x + 225 > getCenterX();
            isCharging = true;
            chargeTimer = 0f;
            chargeCooldownTimer = 0f;
            return;
        }

        if (slamCooldownTimer >= slamCooldown && inRange()) {
            isSlamming = true;
            slamTimer = 0f;
            slamCooldownTimer = 0f;
            velocity.x = 0;
            velocity.y = 800;
            isJumping = true;
            return;
        }

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
                isLookingRight = target.getPosition().x + 225 > getCenterX();
            }
            walk();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();

        Color tint = enraged ? new Color(1f, 0.4f, 0.2f, 1f) : new Color(0.7f, 0.5f, 1f, 1f);
        batch.setColor(tint);
        batch.draw(currentFrame, position.x, position.y, renderWidth, renderHeight);
        batch.setColor(1f, 1f, 1f, 1f);

        if (flashTimer > 0) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.setColor(1f, 1f, 1f, flashTimer / 0.1f);
            batch.draw(currentFrame, position.x, position.y, renderWidth, renderHeight);
            batch.setColor(1f, 1f, 1f, 1f);
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }

        if (!isDead && !isDying()) {
            renderBossHealthBar(batch);
        }

        renderTexts(batch);
    }

    private void renderTexts(SpriteBatch batch) {
        java.util.Iterator<io.github.maingame.utils.DamageText> dIt = damageTexts.iterator();
        while (dIt.hasNext()) {
            io.github.maingame.utils.DamageText dt = dIt.next();
            dt.render(batch);
            dt.update(com.badlogic.gdx.Gdx.graphics.getDeltaTime());
            if (dt.isExpired()) { dt.dispose(); dIt.remove(); }
        }
        java.util.Iterator<io.github.maingame.utils.GoldText> gIt = goldTexts.iterator();
        while (gIt.hasNext()) {
            io.github.maingame.utils.GoldText gt = gIt.next();
            gt.render(batch);
            gt.update(com.badlogic.gdx.Gdx.graphics.getDeltaTime());
            if (gt.isExpired()) { gt.dispose(); gIt.remove(); }
        }
    }

    private void renderBossHealthBar(SpriteBatch batch) {
        float healthPercent = Math.max(0, Math.min(1f, health / maxHealth));
        Texture pixel = TextureManager.getWhitePixel();
        float barWidth = 400f;
        float barHeight = 14f;
        float barX = getCenterX() - barWidth / 2f;
        float barY = position.y + renderHeight + 15f;

        batch.setColor(0.15f, 0.15f, 0.15f, 0.9f);
        batch.draw(pixel, barX - 2, barY - 2, barWidth + 4, barHeight + 4);

        Color barColor = enraged ? new Color(1f, 0.3f, 0.1f, 1f) : new Color(0.6f, 0.2f, 1f, 1f);
        batch.setColor(barColor);
        batch.draw(pixel, barX, barY, barWidth * healthPercent, barHeight);

        batch.setColor(1f, 0.85f, 0f, 0.9f);
        batch.draw(pixel, barX - 3, barY - 3, barWidth + 6, 2);
        batch.draw(pixel, barX - 3, barY + barHeight + 1, barWidth + 6, 2);
        batch.draw(pixel, barX - 3, barY - 3, 2, barHeight + 6);
        batch.draw(pixel, barX + barWidth + 1, barY - 3, 2, barHeight + 6);

        batch.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    protected void checkOnPlatform() {
        if (isSlamming) return;
        super.checkOnPlatform();
    }

    public boolean isBoss() {
        return true;
    }
}
