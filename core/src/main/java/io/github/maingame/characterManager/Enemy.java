package io.github.maingame.characterManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.design2dManager.AnimationManager;

public class Enemy extends character {
    private int health;
    private int damage;
    public Enemy(Vector2 position, AnimationManager animationManager) {
        super(position , animationManager);
        this.health = 50;
        this.damage = 10;
    }

    @Override
    public void update(float delta) {
        move();
        position.add(velocity.scl(delta));
    }

    @Override
    public void render(SpriteBatch batch)
    {
    }

    private void move() {
    }

    public void attack(Player player) {
    }
}
