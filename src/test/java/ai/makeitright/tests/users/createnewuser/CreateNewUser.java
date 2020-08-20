package ai.makeitright.tests.users.createnewuser;

import ai.makeitright.utilities.DriverConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateNewUser extends DriverConfig {

    private String URL;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("inputParameters.url", "http://frontend-mvp-dev.s3-website-us-east-1.amazonaws.com/signin");

    }

    @BeforeMethod
    public void before() {
        URL = System.getProperty("inputParameters.url");
    }

    @Test
    public void createNewUser() {
        driver.get(URL);
        driver.manage().window().maximize();
//        fail("writing of this automated test has not been finished");
    }

}
