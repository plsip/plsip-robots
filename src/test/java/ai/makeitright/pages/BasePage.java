package ai.makeitright.pages;

import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Action;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected WebDriver driver;
    protected String url;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        Main.report.logInfo("Checking if '" + this.getClass().getSimpleName() + " Page'  is open");
        Assert.assertTrue(isAt());
        Main.report.logPass("'" + this.getClass().getSimpleName() + "' is open");
    }


    public BasePage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        PageFactory.initElements(this.driver, this);
        Main.report.logInfo("Checking if '" + this.getClass().getSimpleName() + " Page'  is open");
        Assert.assertTrue(isAt());
        Main.report.logPass("'" + this.getClass().getSimpleName() + "' is open");
    }

    protected abstract boolean isAt();

    public void click(WebElement element, String elementDescription) {
        Main.report.logInfo("Click "+elementDescription+"");
        element.click();
        Main.report.logPass("Element was clicked");
    }

    public void sendSecretlyText(WebElement element, String text, String elementDescription) {
        Main.report.logInfo("Enter password to "+element.getTagName());
        element.sendKeys(text);
        Main.report.logPass("Text was entered");
    }

    public WebElement getItemFromDropdown(WebElement dropdown, List<WebElement> results, String option) {
        try {
            return new Action(driver).getItemFromDropdown(dropdown, results, option);
        } catch (InterruptedException e) {
            Main.report.logFail("There was error while selecting option from dropdown. " + e.getMessage());
            return null;
        }
    }

    public void sendText(WebElement element, String text, String elementDescription) {
        Main.report.logInfo("Enter text '"+text+"' to "+elementDescription);
        element.sendKeys(text);
        Main.report.logPass("Text was entered");
    }

    public boolean waitForBlueCircleDisappear() {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        Main.report.logInfo("Wait max 60s until blue rounding circle disappears...");
        try {
            driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
            new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='Polaris-Spinner Polaris-Spinner--colorHighlight Polaris-Spinner--sizeLarge']")));
        } catch(Exception e) {
            Main.report.logFail("Blue rounding circle was still displayed");
            return false;
        }
        Main.report.logPass("No blue rounding circle is displaying");
        return true;
    }

    public boolean waitForClickable(WebElement element) {
        try {
            new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForVisibilityOf(WebElement element) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
