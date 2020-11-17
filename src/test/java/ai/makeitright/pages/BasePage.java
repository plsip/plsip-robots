package ai.makeitright.pages;

import ai.makeitright.utilities.Action;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected WebDriver driver;
    protected String param;
    protected String urlOrParam;
    private Action action;

    public BasePage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        Main.report.logInfo("Checking if '" + this.getClass().getSimpleName() + " Page'  is open");
        Assert.assertTrue(isAt());
        Main.report.logPass("'" + this.getClass().getSimpleName() + "' is open");
    }

    //todo we should use BasePage method with one parater inside this one, but I see that this.url is not being used in
    //todo this method, then do we need this method?
    public BasePage(final WebDriver driver, final String url) {
        this.driver = driver;
        this.urlOrParam = url;
        PageFactory.initElements(this.driver, this);
        Main.report.logInfo("Checking if '" + this.getClass().getSimpleName() + " Page'  is open");
        Assert.assertTrue(isAt());
        Main.report.logPass("'" + this.getClass().getSimpleName() + "' is open");
    }

    public BasePage(final WebDriver driver, final String url, final String param) {
        this.driver = driver;
        this.urlOrParam = url;
        this.param = param;
        PageFactory.initElements(this.driver, this);
        Main.report.logInfo("Checking if '" + this.getClass().getSimpleName() + " Page'  is open");
        Assert.assertTrue(isAt());
        Main.report.logPass("'" + this.getClass().getSimpleName() + "' is open");
    }

    protected abstract boolean isAt();

    public void click(final WebElement element, final String elementDescription) {
        Main.report.logInfo("Click " + elementDescription);
        element.click();
        Main.report.logPass("Element was clicked");
    }

    public void sendSecretlyText(final WebElement element, final String text, final String elementDescription) {
        Main.report.logInfo("Enter secret text to " + element.getTagName());
        element.sendKeys(text);
        Main.report.logPass("Text was entered");
    }

    public WebElement getItemFromDropdown(final WebElement dropdown, final List<WebElement> results, final String option) {
        try {
            return new Action(driver).getItemFromDropdown(dropdown, results, option);
        } catch (InterruptedException e) {
            Main.report.logFail("There was error while selecting option from dropdown. " + e.getMessage());
            return null;
        }
    }

    public void hoverMouseOver(WebElement element) {
        action = new Action(this.driver);
        action.hoverMouseOver(element);
    }

    public void sendText(final WebElement element, final String text, final String elementDescription) {
        Main.report.logInfo("Enter text '" + text + "' to " + elementDescription);
        element.sendKeys(text);
        Main.report.logPass("Text was entered");
    }

    public void sendTextWithJavascriptExecutor(final WebElement element, final String text, final String elementDescription) {
        Main.report.logInfo("Enter text '" + text + "' to " + elementDescription);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].value='" + text + "';", element);
        Main.report.logPass("Text was entered");
    }

    public boolean waitForBlueCircleDisappear() {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        Main.report.logInfo("Wait max 10s until blue rounding circle disappears...");
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='Polaris-Spinner Polaris-Spinner--colorHighlight Polaris-Spinner--sizeLarge']")));
        } catch (Exception e) {
            Main.report.logFail("Blue rounding circle was still displayed");
            return false;
        }
        Main.report.logPass("No blue rounding circle is displaying");
        return true;
    }

    public boolean waitForBlueCircleDisappearLong() {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        Main.report.logInfo("Wait max 20s until blue rounding circle disappears...");
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='Polaris-Spinner Polaris-Spinner--colorHighlight Polaris-Spinner--sizeLarge']")));
        } catch (Exception e) {
            Main.report.logFail("Blue rounding circle was still displayed");
            return false;
        }
        Main.report.logPass("No blue rounding circle is displaying");
        return true;
    }

    public boolean waitForClickable(final WebElement element) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForVisibilityOf(final WebElement element) {
        try {
            new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitLongForVisibilityOf(final WebElement element) {
        try {
            new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitShortForVisibilityOf(final WebElement element) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForVisibilityOf(final By locator) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForVisibilityOfAllElements(List<WebElement> elements) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
