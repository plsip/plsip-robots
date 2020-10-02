package ai.makeitright.tests.jobs.createnewjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import org.junit.Before;
import org.junit.Test;

public class CreateNewJobTest extends DriverConfig {

    //from configuration
    private String email;
    private String password;
    private String pfCompanyName;
    private String powerFarmUrl;
    private String workflowName;

    //for reporting:
    private String taskname;

    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        taskname = System.getProperty("previousResult.taskname");
    }

    @Test
    public void createNewJob() {

        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfCompanyName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Workflows");

        //WorkflowsPage workflowsPage = new WorkflowsPage(driver);
    }
}
