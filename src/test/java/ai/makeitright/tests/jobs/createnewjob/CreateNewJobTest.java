package ai.makeitright.tests.jobs.createnewjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateNewJobTest extends DriverConfig {

    //from configuration
    private String email;
    private String password;
    private String pfCompanyName;
    private String powerFarmUrl;
    private String workflowName;
    private String argumentsCollection;

    //for reporting:
    private String taskname;

    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
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

        WorkflowsPage workflowsPage = new WorkflowsPage(driver);

        CreateJobModalWindow createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);

        createJobModalWindow
                .clickSaveAndGoToCollectionButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToValuesButton()
                .clickSaveAndGoToScheduleButton()
                .clickCreateJobButton();

        //Your job (ID:M1QQENMHAI) was successfully created!

        //div[@class='Polaris-Stack__Item Polaris-Stack__Item--fill']/span

        //div[@class='Polaris-Stack__Item Polaris-Stack__Item--fill']/span//parent::div

        //h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']
        //Job M1QQENMHAI - Don't use - create new job test

    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create new job");
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



