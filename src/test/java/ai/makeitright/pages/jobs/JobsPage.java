package ai.makeitright.pages.jobs;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobsPage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement jobsHeader;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    @Override
    protected boolean isAt() {
        waitForBlueCircleDisappear();
        return jobsHeader.getText().equals("Jobs");
    }

    public JobsPage(final WebDriver driver) {
        super(driver);
    }

    public JobsPage filterJob(String jobID) {
        Main.report.logInfo("Search job which has ID: '" + jobID + "'");
        sendText(inpFilterItems, jobID, "input element 'Filter items'");
        waitForBlueCircleDisappear();
        return this;
    }

    public JobsTable getJobsTable() {
        return new JobsTable(driver);
    }

    public JobDetailsPage clickFoundJob(final String jobID) {
        WebElement job = getJobsTable().getDesirableRow(jobID);
        click(job, "found job");
        return new JobDetailsPage(driver);
    }
}
