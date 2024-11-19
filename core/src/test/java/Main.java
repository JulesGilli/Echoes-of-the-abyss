import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running automated tests...");

        Result result = JUnitCore.runClasses(GameStatTest.class, EntityTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("Test failed: " + failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests passed successfully!");
        } else {
            System.out.println("Some tests failed. Total failures: " + result.getFailureCount());
        }
    }
}

