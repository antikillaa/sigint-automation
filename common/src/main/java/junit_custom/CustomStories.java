package junit_custom;

import failure_strategy.Statistic;
import org.apache.log4j.Logger;
import org.jbehave.core.junit.JUnitStories;
import org.junit.Test;

public abstract class CustomStories extends JUnitStories {
    
    private Logger logger = Logger.getLogger(CustomStories.class);
    
    @Test
    public void run() {
        try {
            super.run();
        } catch (Throwable throwable) {
            if (new Statistic().hasFailuresWithoutBugs()) {
                throw new Error();
            }
        }
    
    
    }
   
}
