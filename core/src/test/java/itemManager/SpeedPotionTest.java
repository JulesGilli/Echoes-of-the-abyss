package itemManager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SpeedPotionTest
{
    GameStat stat = new GameStat();
    Player player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
        Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
        Input.Keys.F, Input.Keys.SHIFT_LEFT,Input.Keys.E);

    @Test
    public void applyItemTest()
    {

    }
}
