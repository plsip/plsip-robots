package ai.makeitright.tests.settings;

import ai.makeitright.utilities.DriverConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssignRepository extends DriverConfig {
    private String URL;
    private String URLGITLAB;

//    @BeforeClass
//    public static void beforeClass() {
//        System.setProperty("inputParameters.url","http://development.powerfarm.ai");
//        System.setProperty("inputParameters.urlgitlab","https://gitlab.com/");
//        System.setProperty("inputParameters.usergitlab","kraczkowska");
//        System.setProperty("inputParameters.passwordgitlab","https://gitlab.com/");
//    }

    @Before
    public void before() {
        URL = System.getProperty("inputParameters.url");
    }

    @Test
    public void assignRepository() {
        driver.get(URL);
        driver.manage().window().maximize();

//        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(URL);
        String pageTitle = driver.getTitle();
        System.setProperty("output", String.format("{\"result\":{\"url\":\"%s\",\"title_of_page\":\"%s\"}}",URL,pageTitle));
    }
}
