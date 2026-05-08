package io.github.maingame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeathParticle {
    private static final float PARTICLE_SIZE = 6f;
    private static final float LIFETIME = 0.8f;

    private final Vector2 position;
    private final Vector2 velocity;
    private final Color color;
    private float timer;
    private float alpha = 1f;

    private DeathParticle(Vector2 position, Vector2 velocity, Color color) {
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
        this.color = new Color(color);
        this.timer = 0f;
    }

    public static List<DeathParticle> spawn(float x, float y, int count, Color baseColor) {
        List<DeathParticle> particles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float angle = MathUtils.random(0f, 360f);
            float speed = MathUtils.random(100f, 400f);
            float vx = MathUtils.cosDeg(angle) * speed;
            float vy = MathUtils.sinDeg(angle) * speed;

            Color c = new Color(
                MathUtils.clamp(baseColor.r + MathUtils.random(-0.15f, 0.15f), 0, 1),
                MathUtils.clamp(baseColor.g + MathUtils.random(-0.15f, 0.15f), 0, 1),
                MathUtils.clamp(baseColor.b + MathUtils.random(-0.15f, 0.15f), 0, 1),
                1f
            );
            particles.add(new DeathParticle(new Vector2(x, y), new Vector2(vx, vy), c));
        }
        return particles;
    }

    public void update(float delta) {
        timer += delta;
        position.add(velocity.x * delta, velocity.y * delta);
        velocity.y -= 500f * delta;
        alpha = 1f - timer / LIFETIME;
    }

    public void render(SpriteBatch batch, Texture whitePixel) {
        Color prev = batch.getColor().cpy();
        batch.setColor(color.r, color.g, color.b, alpha);
        batch.draw(whitePixel, position.x, position.y, PARTICLE_SIZE, PARTICLE_SIZE);
        batch.setColor(prev);
    }

    public boolean isExpired() {
        return timer >= LIFETIME;
    }

    public static void updateAndRender(List<DeathParticle> particles, float delta, SpriteBatch batch, Texture whitePixel) {
        for (Iterator<DeathParticle> it = particles.iterator(); it.hasNext(); ) {
            DeathParticle p = it.next();
            p.update(delta);
            p.render(batch, whitePixel);
            if (p.isExpired()) {
                it.remove();
            }
        }
    }
}
