package ai.makeitright.tests.alltest;

import ai.makeitright.tests.globalarguments.singleaddargument.AddArgument71Test;
import org.testng.annotations.Test;

public class AllTests {

    @Test
    public void tests() throws InterruptedException {
        AddArgument71Test test71 = new AddArgument71Test();
        test71.before();
        test71.addArgument();
        test71.prepareJson();
    }
}
