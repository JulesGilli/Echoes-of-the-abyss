package classTest.itemsTest;
import fictitiousClass.TestPlayer;
import io.github.maingame.core.GameStat;
import io.github.maingame.items.ArmorPotion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class ArmorPotionTest {
    private TestPlayer player;
    private ArmorPotion armorPotion;
    private GameStat stat;

    @BeforeClass
    public static void setupClass() {
        System.out.println("\n=== Setup: Initializing tests for Entity===\n");
    }

    @Before
    public void setUp() {
        player = new TestPlayer();
        stat = new GameStat();
        armorPotion = new ArmorPotion(stat);
        stat.setFloors(4);
        System.out.println("Initialized a new Entity instance with default values.");
    }

    @Test
    public void testApplyItem(){
        System.out.println("\n=== Testing applyItem() method ===\n");
        armorPotion.applyItem(player);
        assertEquals("The player should have 40 of Armor",stat.getFloors() * 10, player.getArmor(), 0.0);
    }

    @Test
    public void testResetItem(){
        System.out.println("\n=== Testing resetItem() method ===\n");
        armorPotion.applyItem(player);
        armorPotion.resetItem(player);
        assertEquals("The player should have 0 of Armor",0, player.getArmor(), 0.0);
    }

    @Test
    public  void testIsUnlocked(){
        System.out.println("\n=== Testing isUnlocked() method ===\n");
        stat.setMaxFloors(7);
        assertTrue("armor should be unlock", armorPotion.isUnlocked(stat));
        stat.setMaxFloors(6);
        assertFalse("armor should be lock", armorPotion.isUnlocked(stat));
    }

    @After
    public void after() {
        System.out.println("=== After: Test completed. Cleaning up. ===\n");
    }

    @AfterClass
    public static void teardownClass() {
        System.out.println("\n=== AfterClass: All tests completed for Entity ===\n");
    }
}
