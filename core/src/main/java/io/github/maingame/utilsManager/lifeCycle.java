package io.github.maingame.utilsManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.Enemy;

import java.util.List;

public interface lifeCycle {
    void update(float delta);
    void render(SpriteBatch batch);
}
