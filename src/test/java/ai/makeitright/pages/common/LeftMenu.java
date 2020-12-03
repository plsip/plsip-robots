package ai.makeitright.pages.common;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class LeftMenu extends BasePage {
    public LeftMenu(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForBlueCircleDisappear();
        if(!waitForVisibilityOf(leftMenu)) {
            Main.report.logFail("There is no visible left menu element");
        }
        Assert.assertTrue(waitForVisibilityOf(leftMenu), "There is no visible left menu element");
        return leftMenu.isDisplayed();
    }

    @FindBy(xpath = "//ul[@class='Polaris-Navigation__Section'][1]/li[1]")
    private WebElement leftMenu;

    @FindAll (
        @FindBy(xpath = "//ul/li/div//a/span")
    )
    private List<WebElement> leftMenuText;

    private By getLeftMenuOption(final String menuOption) {
        return new By.ByXPath("//ul/li/div//span[contains(text(),'" + menuOption + "')]");
    }

    public boolean checkListOfOption(String[] leftMenuOptionsArray) {
        for (int i=0;i<leftMenuOptionsArray.length;i++) {
            Assert.assertEquals(leftMenuText.get(i).getText(),leftMenuOptionsArray[i],"There is not expected option on left menu on position " + i + ". Should be " + leftMenuOptionsArray[i] + "but was: " + leftMenuText.get(i).getText());
        }
        return true;
    }

    public void openPageBy(final String menuOption) {
        waitForBlueCircleDisappear();
        Assert.assertTrue(wait10ForClickable(driver.findElement(getLeftMenuOption(menuOption))),"Waiting 5s for left menu to be clickable return false");
        click(driver.findElement(getLeftMenuOption(menuOption)),"option " + menuOption + " from left menu");
    }
}
