package io.github.maingame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.CharacterManager.Player;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private Player player;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.batch;
        this.player = new Player(new Vector2(100, 100));
    }

    @Override
    public void render(float delta) {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.render(batch);
        player.update(delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Gérer le redimensionnement de l'écran si nécessaire
    }

    @Override
    public void show() {
        // Cette méthode est appelée lorsque cet écran devient l'écran actuel du jeu
    }

    @Override
    public void hide() {
        // Cette méthode est appelée lorsque cet écran n'est plus l'écran actuel du jeu
    }

    @Override
    public void pause() {
        // Cette méthode est appelée lorsque le jeu est mis en pause
    }

    @Override
    public void resume() {
        // Cette méthode est appelée lorsque le jeu reprend après une pause
    }

    @Override
    public void dispose() {
        // Libérer les ressources de cet écran
    }
}
