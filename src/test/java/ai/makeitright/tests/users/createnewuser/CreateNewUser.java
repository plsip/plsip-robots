package ai.makeitright.tests.users.createnewuser;

import ai.makeitright.utilities.DriverConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateNewUser extends DriverConfig {

    private String URL;

    @BeforeAll
    public static void beforeClass() {
        System.setProperty("inputParameters.url","http://frontend-mvp-dev.s3-website-us-east-1.amazonaws.com/signin");

    }

    @BeforeEach
    public void before() {
        URL = System.getProperty("inputParameters.url");
    }

    @Test
    public void createNewUser() {
        driver.get(URL);
        driver.manage().window().maximize();
    }

}
