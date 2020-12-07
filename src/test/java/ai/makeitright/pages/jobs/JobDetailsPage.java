package ai.makeitright.pages.jobs;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.tasks.TaskDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobDetailsPage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement jobHeader;

    @FindBy(xpath = "//span[text()='STATUS']/following-sibling::div")
    private WebElement jobStatus;

    @FindBy(xpath = "//span[text()='JOB ID']/following-sibling::p")
    private WebElement jobID;

    @FindBy(xpath = "//h2[text()='Execution']/../..//span[text()='CREATED BY']/following-sibling::p")
    private WebElement txtJobCreatedBy;

    @FindBy(xpath = "//div[text()='Test plan information ']")
    private WebElement sectionTestPlanInformation;

    @FindBy(xpath = "//div[text()='Workflow information ']")
    private WebElement sectionWorkflowInformation;

    @FindBy(xpath = "//div[text()='Jira Integration']")
    private WebElement sectionJiraIntegration;

    @FindBy(xpath = "//span[text()='TYPE']/following-sibling::p")
    private WebElement txtWorkflowType;

    @FindBy(xpath = "//button//span[text()='Delete']")
    private WebElement btnDeleteJobEnabled;

    @FindBy(xpath = "//button//span[text()='Rerun Job']")
    private WebElement btnRerunJobEnabled;

    @FindBy(xpath = "//button//span[text()='Show results']")
    private WebElement btnShowResults;

    public JobDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForBlueCircleDisappear();
        return jobHeader.isDisplayed();
    }

    public TaskDetailsPage clickLnkName(WebElement lnkName, String name) {
        click(lnkName, "link of task/test with name '" + name + "'");
        return new TaskDetailsPage(driver);
    }

    public JobsDetailsTasksTable getJobsDetailsTasksTable() {
        return new JobsDetailsTasksTable(driver);
    }

    public String getJobStatus() {
        return jobStatus.getText().replace("\n", " ");
    }

    public String getJobID() {
        return jobID.getText();
    }

    public String getJobHeader() {
        return jobHeader.getText();
    }

    public boolean checkCreatedBy() {
        return (new TopPanel(driver).getCreatedBy()).equals(txtJobCreatedBy.getText());
    }

    public boolean checkTestPlanInformationIsVisible() {
        return sectionTestPlanInformation.isDisplayed();
    }

    public boolean checkWorkflowInformationIsVisible() {
        return sectionWorkflowInformation.isDisplayed();
    }

    public boolean checkJiraIntegrationIsVisible() {
        return sectionJiraIntegration.isDisplayed();
    }

    public boolean checkButtonDeleteJobIsEnabled() {
        return btnDeleteJobEnabled.isEnabled();
    }

    public boolean checkButtonRerunJobIsEnabled() {
        return btnRerunJobEnabled.isEnabled();
    }

    public boolean checkResultsButtonExist() {
        return btnShowResults.isDisplayed();
    }
}
