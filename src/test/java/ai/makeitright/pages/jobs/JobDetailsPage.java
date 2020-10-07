package ai.makeitright.pages.jobs;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.TopPanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobDetailsPage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement jobHeader;

    @FindBy(xpath = "//span[text()='JOB ID']/following-sibling::p")
    private WebElement jobID;

    public JobDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return jobHeader.isDisplayed();
    }

    public boolean checkJobID(String id) {
        return jobID.getText().equals(id);
    }

    public boolean checkJobHeader(String msg) {
        return jobHeader.getText().equals(msg);
    }

    public String getJobHeader() {
        return jobHeader.getText();
    }
}
