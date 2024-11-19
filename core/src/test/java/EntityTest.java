import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityTest {

    private TestEntity testEntity;

    @Before
    public void setup() {
        // Initialisation de l'entité de test avec des valeurs arbitraires
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
}
