package junit_custom;

import failure_strategy.Failures;
import org.jbehave.core.junit.JUnitStories;
import org.junit.Test;

public abstract class CustomStories extends JUnitStories {
    
    @Test
    public void run() {
        try {
            super.run();
        } catch (Throwable throwable) {
            if (Failures.hasFailuresWithoutBugs()) {
                throw new RuntimeException();
            }
        }
    
    
    }
   
}
