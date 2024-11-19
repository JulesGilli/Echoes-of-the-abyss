import classTest.utilsManagerTest.GameStatTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class Main {

    public static void main(String[] args) {
        System.out.println("Running tests...");
        TestRunner runner = new TestRunner();
        runner.runTests();
    }
}

