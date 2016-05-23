package verifier;

import conditions.ExpectedConditions;
import org.junit.Assert;

public class Verify {


    public static void shouldBe(ExpectedConditions conditions) {

        Assert.assertTrue(conditions.check());
    }
}
