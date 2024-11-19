package classTest.characterManagerTest;
import fictitiousClass.TestEntity;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {

    private TestEntity testEntity;

    @Before
    public void setup() {
        testEntity = new TestEntity(new Vector2(0, 0), 100, 50, 20);
    }

    @Test
    public void testReceiveDamage() {
        System.out.println("=== Test: receiveDamage ===");
        testEntity.setArmor(5);
        System.out.println("Initial Health: " + testEntity.getHealth());
        System.out.println("Armor: " + testEntity.getArmor());
        testEntity.receiveDamage(20);
        System.out.println("Health after receiving 20 damage: " + testEntity.getHealth());
        assertEquals(85, testEntity.getHealth(), 0.1);
    }

    @Test
    public void testIsAlive() {
        System.out.println("=== Test: isAlive ===");
        testEntity.setHealth(10);
        assertTrue(testEntity.isAlive());

        testEntity.setHealth(0);
        assertTrue(!testEntity.isAlive());
    }

    @Test
    public void testGetAttack() {
        System.out.println("=== Test: getAttack ===");
        testEntity.setAttackBonus(10);
        assertEquals(30, testEntity.getAttack(), 0.1);
    }

    @Test
    public void testJump() {
        System.out.println("=== Test: jump ===");
        testEntity.jump();
        assertTrue(testEntity.isJumping());
    }

    @Test
    public void testSequentialOperations() {
        System.out.println("=== Test: Sequential Operations ===");
        TestEntity entity = new TestEntity(new Vector2(0, 0), 100, 50, 20);

        entity.setGold(100);
        System.out.println("Gold after addition: " + entity.getGold());
        assertEquals(100, entity.getGold());

        entity.receiveDamage(30);
        System.out.println("Health after damage: " + entity.getHealth());
        assertEquals(70, entity.getHealth(), 0.1);

        assertTrue(entity.isAlive());

        entity.receiveDamage(100);
        System.out.println("Health after fatal damage: " + entity.getHealth());
        assertFalse(entity.isAlive());
    }

}
