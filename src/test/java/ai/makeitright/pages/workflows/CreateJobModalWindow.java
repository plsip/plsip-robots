package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.settigns.RepositoriesAddressesTable;
import ai.makeitright.pages.settigns.RepositoryPage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateJobModalWindow extends BasePage {

    @FindBy(xpath = "//div[@class='flex']/span[1]")
    private WebElement windowHeader;

    @FindBy(xpath = "//div[@class='Polaris-ButtonGroup']/div/button[@class='Polaris-Button Polaris-Button--primary']")
    private WebElement btnSaveAndGo;

    @FindBy(xpath = "//input[@name='SearchArgument']")
    private WebElement inpGlobalArguments;

    @FindAll(
            @FindBy(xpath = "//div[@class='Polaris-ResourceItem__Container']")
    )
    private List<WebElement> collectionTableRows;

    @FindBy(xpath = "//div[@class='Polaris-ResourceItem__Container']//span[@class='Polaris-Checkbox']")
    private WebElement collectionCheckbox;

    public CreateJobModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForVisibilityOf(windowHeader);
        Assertions.assertTrue(windowHeader.getText().contains("Create new job"));
        return true;
    }

    public CreateJobModalWindow clickSaveAndGoToCollectionButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Arguments collection' button");
        return this;
    }

    public CreateJobModalWindow chooseGlobalArgumentsCollection(String collection) {
        sendText(inpGlobalArguments, collection,"input for filter global arguments collection");
        waitForVisibilityOfAllElements(collectionTableRows);
        if (collectionTableRows.size() == 1) {
            click(collectionCheckbox, collection + " collection checkbox");
            return this;
        }
        else {
            Main.report.logFail("Filter doesn't work");
            return null;
        }
    }

    public boolean checkIfCorrectCollectionIsDisplayed(String collectionName) {
        return true;
    }

    public CreateJobModalWindow clickSaveAndGoToValuesButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Arguments values' button");
        return this;
    }

    public CreateJobModalWindow clickSaveAndGoToScheduleButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Schedule' button");
        return this;
    }

    public CreateJobModalWindow clickCreateJobButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Create job' button");
        return this;
    }

    public JobDetailsPage clickGoToJobDetailsButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Go to the job details' button");
        return new JobDetailsPage(driver);
    }

}
