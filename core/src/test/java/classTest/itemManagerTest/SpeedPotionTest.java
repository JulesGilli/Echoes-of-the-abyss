package classTest.itemManagerTest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.Player;
import io.github.maingame.itemManager.SpeedPotion;
import io.github.maingame.utilsManager.GameStat;
import org.junit.*;

public class SpeedPotionTest
{
    private GameStat stat;
    private Player player;
    private SpeedPotion speedPotion;
    private float initialSpeed;

    @Test()
    public void applyItemTest()
    {
        stat = new GameStat();
        player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
            Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
            Input.Keys.F, Input.Keys.SHIFT_LEFT,Input.Keys.E);
        speedPotion = new SpeedPotion(stat);
        initialSpeed = player.getSpeed();
        System.out.println("Setting up before test.");
        float expectedSpeed = initialSpeed + speedPotion.getIncreaseValue();
        speedPotion.applyItem(player);
        Assert.assertEquals(expectedSpeed, player.getSpeed(), 0.0f);
    }

    @Test()
    public void resetItemTest(){
        stat = new GameStat();
        player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
            Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
            Input.Keys.F, Input.Keys.SHIFT_LEFT,Input.Keys.E);
        speedPotion = new SpeedPotion(stat);
        initialSpeed = player.getSpeed();
        System.out.println("Setting up before test.");
        speedPotion.resetItem(player);
        Assert.assertEquals(initialSpeed, player.getSpeed(), 0.0f);
    }

    @Test()
    public void isUnlockedTest(){
        stat = new GameStat();
        player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
            Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
            Input.Keys.F, Input.Keys.SHIFT_LEFT,Input.Keys.E);
        speedPotion = new SpeedPotion(stat);
        initialSpeed = player.getSpeed();
        System.out.println("Setting up before test.");
        stat.setMaxFloors(5);
        Assert.assertTrue(speedPotion.isUnlocked(stat));
        stat.setMaxFloors(2);
        Assert.assertFalse(speedPotion.isUnlocked(stat));
    }

}
