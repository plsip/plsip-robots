package ai.makeitright.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Action {

    private final Actions actions;
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait waitShort;


    public Action(WebDriver driver) {
        this.driver = driver;
        actions = new Actions(this.driver);
        wait = new WebDriverWait(driver, 15);
        waitShort = new WebDriverWait(driver, 3);
    }


    public WebElement getItemFromTable(List<WebElement> menuList, String option) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Main.report.logInfo("Search option '" + option + "'");
        if (menuList.isEmpty()) {
            throw new InterruptedException("No options");
        }
        for (WebElement item : menuList) {
            if (item.getText().equals(option)) {
                Main.report.logPass("The option '" + item.getText() + "' was found");
                return item;
            }
        }
        Main.report.logFail("The option '" + option + "' doesn't exist");
        throw new InterruptedException("The option '" + option + "' doesn't exist");
    }

    public WebElement getItemFromTable(List<WebElement> menuList, int option) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Main.report.logInfo("Search option '" + option + "'");
        if (menuList.isEmpty()) {
            throw new InterruptedException("No options");
        }
        return menuList.get(option - 1);
    }

    public WebElement getItemFromDropdown(WebElement dropdown, List<WebElement> results, int option) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Main.report.logInfo("Click dropdown");
        dropdown.click();
        Main.report.logPass("Dropdown was clicked");
        waitShort.until(ExpectedConditions.visibilityOf(results.get(0)));
        try {
            return results.get(option - 1);
        } catch (Exception e) {
            Main.report.logFail("The option number '" + option + "' doesn't exist in dropdown");
        }
        return results.get(option - 1);
    }

    public WebElement getItemFromDropdownXpathResults(WebElement dropdown, String resultsString, int option) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Main.report.logInfo("Click dropdown");
        dropdown.click();
        Main.report.logPass("Dropdown was clicked");
        wait.until(ExpectedConditions.visibilityOfAllElements(dropdown.findElements(By.xpath(resultsString))));
        List<WebElement> results = dropdown.findElements(By.xpath(resultsString));
        try {
            return results.get(option - 1);
        } catch (Exception e) {
            Main.report.logFail("The option number '" + option + "' doesn't exist in dropdown");
        }
        return results.get(option - 1);
    }

    public WebElement getItemFromDropdown(WebElement dropdown, List<WebElement> results, String option) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Main.report.logInfo("Click dropdown");
        dropdown.click();
        Main.report.logPass("Dropdown was clicked");
        waitShort.until(ExpectedConditions.visibilityOf(results.get(0)));
        for (WebElement item : results) {
            Main.report.logInfo("#Check option '" + item.getText() + "'");
            if (item.getText().trim().equals(option)) {
                Main.report.logInfo("+The option '" + option + "' was found");
                return item;
            }
        }
        Main.report.logFail("The option '" + option + "' doesn't exist in dropdown");
        throw new InterruptedException("The option '" + option + "' doesn't exist in dropdown");
    }

    public WebElement getItemFromUnorderedList(WebElement unorderedList, List<WebElement> itemsOfUnorderedList, String itemOfList) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Main.report.logInfo("Search listItem '" + itemOfList + "'");
        if (itemsOfUnorderedList.isEmpty()) {
            throw new InterruptedException("Unordered list is empty");
        }
        for (WebElement item : itemsOfUnorderedList) {
            if (item.getText().equals(itemOfList)) {
                Main.report.logPass("The item '" + item.getText() + "' was found in unordered list.");
                return item;
            }
        }
        Main.report.logFail("The list item '" + itemOfList + "' doesn't exist in unerdered list.");
        throw new InterruptedException("The option '" + itemOfList + "' doesn't exist in underdered list.");
    }

    public void hoverMouseOver(WebElement element) {
        Main.report.logInfo("Move mouse to element");
        actions.moveToElement(element).perform();
        Main.report.logPass("Mouse was moved to element");
    }

	public void sendText(WebElement element, String text, String elementDescription) {
		Main.report.logInfo("Enter text '" + text + "' to " + elementDescription);
		actions.sendKeys(element, text).build().perform();
		Main.report.logPass("Text was entered");
	}

}
