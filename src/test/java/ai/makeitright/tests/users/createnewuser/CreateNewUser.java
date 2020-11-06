package ai.makeitright.tests.users.createnewuser;

import ai.makeitright.utilities.DriverConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateNewUser extends DriverConfig {

    private String url;

    @BeforeTest
    public void before() {
        url = System.getProperty("inputParameters.url");
    }

    @Test
    public void createNewUser() {
        driver.get(url);
        driver.manage().window().maximize();
        Assert.fail("writing of this automated test has not been finished");
    }

}
