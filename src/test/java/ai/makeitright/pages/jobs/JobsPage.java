package ai.makeitright.pages.jobs;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class JobsPage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement jobsHeader;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> lstJobRow;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    private By getHeadersName(int headerNumber) {
        return new By.ByXPath("//thead/tr/th[" + headerNumber + "]/button");
    }

    @Override
    protected boolean isAt() {
        Assert.assertTrue(waitForBlueCircleDisappear());
        return jobsHeader.getText().equals("Jobs");
    }

    public JobsPage(final WebDriver driver) {
        super(driver);
    }

    public boolean checkForColumnNumberHeaderHasValue(int headerNumber, String expectedHeaderName) {
        String headersName = driver.findElement(getHeadersName(headerNumber)).getText();
        Main.report.logInfo("Actual headers name is '" + headersName + "'");
        return headersName.equals(expectedHeaderName);
    }

    public boolean checkIfOneJobIsDisplayed() {
        return tableRows.size() == 1;
    }

    public boolean checkJobsFromFirstPaginationPageContainValuesInColumns() {
        for (WebElement row : lstJobRow) {
            Assert.assertNotEquals(row.findElement(By.xpath("./th/a")).getText(),"","In first column row doesn't contain value");
            for (int i=1;i<7;i++) {
                Assert.assertNotEquals(row.findElement(By.xpath("./td["+i+"]")).getText(),"","In " + i + " column row doesn't contain value");
            }
        }
        return true;
    }

    public JobDetailsPage clickFoundJob(final String jobID) {
        WebElement job = getJobsTable().getDesirableRow(jobID);
        click(job, "found job");
        return new JobDetailsPage(driver);
    }

    public JobDetailsPage clickJobID(WebElement lnkID, String jobID) {
        click(lnkID,"ID of job '" + jobID + "'");
        return new JobDetailsPage(driver);
    }

    public void clickJobsText() {
        click(jobsHeader,"header 'Jobs'");
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

    public boolean isJobRowDisplayed() {
        return lstJobRow.size() > 0;
    }

}
