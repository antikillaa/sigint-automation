package conditions;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class Verify {

    private static Logger log = Logger.getLogger(Verify.class);

    public static void shouldBe(ExpectedCondition condition) {
        try {
        Assert.assertTrue(condition.check()); }
        catch (AssertionError e) {
            log.error("Error comparing with condition:"+condition+".Should be true but got false");
            throw e;
        }
    }

    public static void shouldNotBe(ExpectedCondition condition) {
        try {
            Assert.assertFalse(condition.check());
        } catch (AssertionError e) {
            log.error(String.format("Error comparing with condition %s. Should be false but got true", condition));
        }
    }
}
