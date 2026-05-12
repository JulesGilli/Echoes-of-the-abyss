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
import io.github.maingame.utils.DeathParticle;
import io.github.maingame.utils.Platform;
import io.github.maingame.utils.TextureManager;

import java.util.ArrayList;
import java.util.List;

public class BossEnemy extends Enemy {
    private static final float SLAM_DAMAGE_RADIUS = 400f;
    private static final float SLAM_WINDUP_DURATION = 0.5f;
    private static final float CHARGE_WINDUP_DURATION = 0.6f;
    private static final float CHARGE_RANGE = 1400f;

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
    private float slamTargetX = 0f;
    private boolean isChargeWindingUp = false;
    private float chargeWindupTimer = 0f;
    private boolean isSlamWindingUp = false;
    private float slamWindupTimer = 0f;
    private float telegraphSpawnTimer = 0f;
    private final List<DeathParticle> slamParticles = new ArrayList<>();

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
            22,
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

        if (isChargeWindingUp) {
            chargeWindupTimer += delta;
            velocity.x = 0;
            spawnChargeTelegraphParticles(delta);
            if (chargeWindupTimer >= CHARGE_WINDUP_DURATION) {
                isChargeWindingUp = false;
                isCharging = true;
                chargeTimer = 0f;
            }
            return;
        }

        if (isCharging) {
            chargeTimer += delta;
            float chargeDir = isLookingRight ? 1f : -1f;
            velocity.x = chargeDir * chargeSpeed;
            if (chargeTimer >= 0.6f) {
                isCharging = false;
                chargeTimer = 0f;
                velocity.x = 0;
                if (inRange()) {
                    target.receiveDamage(attackDamage * 1.25f);
                }
            }
            return;
        }

        if (isSlamWindingUp) {
            slamWindupTimer += delta;
            velocity.x = 0;
            velocity.y = 0;
            spawnTelegraphParticles(delta);
            if (slamWindupTimer >= SLAM_WINDUP_DURATION) {
                isSlamWindingUp = false;
                isSlamming = true;
                slamTimer = 0f;
                velocity.y = 800;
                isJumping = true;
            }
            return;
        }

        if (isSlamming) {
            slamTimer += delta;
            if (slamTimer >= 0.3f && position.y > 0) {
                velocity.y = -1500;
            }

            spawnTelegraphParticles(delta);

            if (position.y <= 0 && slamTimer >= 0.3f) {
                isSlamming = false;
                slamTimer = 0f;
                velocity.x = 0;
                velocity.y = 0;
                position.y = 0;
                isJumping = false;
                spawnImpactParticles();
                float targetCenterX = target.getPosition().x + 225;
                float dist = Math.abs(targetCenterX - slamTargetX);
                if (dist < SLAM_DAMAGE_RADIUS) {
                    target.receiveDamage(attackDamage * 1.5f);
                }
            }
            return;
        }

        if (chargeCooldownTimer >= chargeCooldown && !inRange()) {
            isLookingRight = target.getPosition().x + 225 > getCenterX();
            isChargeWindingUp = true;
            chargeWindupTimer = 0f;
            chargeCooldownTimer = 0f;
            telegraphSpawnTimer = 0f;
            return;
        }

        if (slamCooldownTimer >= slamCooldown && inRange()) {
            isSlamWindingUp = true;
            slamWindupTimer = 0f;
            slamCooldownTimer = 0f;
            velocity.x = 0;
            slamTargetX = getCenterX();
            telegraphSpawnTimer = 0f;
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

    private void spawnTelegraphParticles(float delta) {
        telegraphSpawnTimer += delta;
        // ~30 particles per second along the ground in the damage zone
        if (telegraphSpawnTimer >= 0.033f) {
            telegraphSpawnTimer = 0f;
            float offset = MathUtils.random(-SLAM_DAMAGE_RADIUS, SLAM_DAMAGE_RADIUS);
            float px = slamTargetX + offset;
            float py = MathUtils.random(0f, 30f);
            Color warn = enraged
                ? new Color(1f, 0.35f, 0.1f, 1f)
                : new Color(1f, 0.85f, 0.2f, 1f);
            // Single low-velocity spark — reuse DeathParticle as a generic particle
            slamParticles.addAll(DeathParticle.spawn(px, py, 1, warn));
        }
    }

    private void spawnChargeTelegraphParticles(float delta) {
        telegraphSpawnTimer += delta;
        if (telegraphSpawnTimer >= 0.033f) {
            telegraphSpawnTimer = 0f;
            float dir = isLookingRight ? 1f : -1f;
            float laneStart = getCenterX();
            float offset = MathUtils.random(0f, CHARGE_RANGE);
            float px = laneStart + dir * offset;
            float py = MathUtils.random(0f, 60f);
            Color warn = enraged
                ? new Color(1f, 0.35f, 0.1f, 1f)
                : new Color(1f, 0.85f, 0.2f, 1f);
            slamParticles.addAll(DeathParticle.spawn(px, py, 1, warn));
        }
    }

    private void spawnImpactParticles() {
        Color impact = enraged
            ? new Color(1f, 0.3f, 0.05f, 1f)
            : new Color(1f, 0.6f, 0.1f, 1f);
        // Burst at impact point along the ground
        for (int i = 0; i < 6; i++) {
            float offset = MathUtils.random(-SLAM_DAMAGE_RADIUS, SLAM_DAMAGE_RADIUS);
            slamParticles.addAll(DeathParticle.spawn(slamTargetX + offset, 20f, 8, impact));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        renderSlamDangerZone(batch);
        renderChargeDangerZone(batch);
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

        DeathParticle.updateAndRender(
            slamParticles,
            com.badlogic.gdx.Gdx.graphics.getDeltaTime(),
            batch,
            TextureManager.getWhitePixel()
        );
    }

    private void renderSlamDangerZone(SpriteBatch batch) {
        if (!isSlamming && !isSlamWindingUp) return;
        Texture pixel = TextureManager.getWhitePixel();
        float t = isSlamWindingUp ? slamWindupTimer : (SLAM_WINDUP_DURATION + slamTimer);
        float pulse = 0.25f + 0.15f * MathUtils.sin(t * 18f);
        Color zone = enraged
            ? new Color(1f, 0.2f, 0.1f, pulse)
            : new Color(1f, 0.7f, 0.1f, pulse);
        batch.setColor(zone);
        batch.draw(pixel, slamTargetX - SLAM_DAMAGE_RADIUS, 0f, SLAM_DAMAGE_RADIUS * 2f, 30f);
        batch.setColor(zone.r, zone.g, zone.b, Math.min(1f, pulse * 2f));
        batch.draw(pixel, slamTargetX - SLAM_DAMAGE_RADIUS, 30f, SLAM_DAMAGE_RADIUS * 2f, 3f);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void renderChargeDangerZone(SpriteBatch batch) {
        if (!isChargeWindingUp) return;
        Texture pixel = TextureManager.getWhitePixel();
        float pulse = 0.25f + 0.15f * MathUtils.sin(chargeWindupTimer * 18f);
        Color zone = enraged
            ? new Color(1f, 0.2f, 0.1f, pulse)
            : new Color(1f, 0.7f, 0.1f, pulse);
        float dir = isLookingRight ? 1f : -1f;
        float laneStart = getCenterX();
        float laneX = dir > 0 ? laneStart : laneStart - CHARGE_RANGE;
        float laneHeight = 80f;
        batch.setColor(zone);
        batch.draw(pixel, laneX, 0f, CHARGE_RANGE, laneHeight);
        batch.setColor(zone.r, zone.g, zone.b, Math.min(1f, pulse * 2f));
        batch.draw(pixel, laneX, laneHeight, CHARGE_RANGE, 3f);
        batch.setColor(1f, 1f, 1f, 1f);
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
        if (isSlamming || isSlamWindingUp) return;
        super.checkOnPlatform();
    }

    public boolean isBoss() {
        return true;
    }
}
