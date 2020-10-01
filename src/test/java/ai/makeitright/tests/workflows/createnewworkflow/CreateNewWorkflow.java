package ai.makeitright.tests.workflows.createnewworkflow;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import org.junit.Before;
import org.junit.Test;

public class CreateNewWorkflow extends DriverConfig {

    //from configuration
    private String email;
    private String password;
    private String pfCompanyName;
    private String powerFarmUrl;
    private String workflowName;
    private String workflowType;


    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        workflowType = System.getProperty("inputParameters.workflowType");
    }

    @Test
    public void createNewWorkflow() {

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
