package fictitiousClass;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.characterManager.Entity;

public class TestEntity extends Entity {

    public TestEntity(Vector2 position, float health, int gold, float attack) {
        super(position, null, health, gold, attack);
    }

    @Override
    public void update(float delta) {
        // Implémentation vide pour les tests
    }

    @Override
    public void render(SpriteBatch batch) {
        // Implémentation vide pour les tests
    }
}
