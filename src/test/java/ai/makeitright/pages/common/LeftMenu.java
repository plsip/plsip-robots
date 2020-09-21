package ai.makeitright.pages.common;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        Assertions.assertTrue(waitForVisibilityOf(leftMenu), "There is no visible left menu element");
        return leftMenu.isDisplayed();
    }

    @FindBy(xpath = "//ul[@class='Polaris-Navigation__Section'][1]/li[1]")
    private WebElement leftMenu;

    private By getLeftMenuOption(final String menuOption) {
        return new By.ByXPath("//ul/li/div//span[contains(text(),'" + menuOption + "')]");
    }

    public void openPageBy(final String menuOption) {
        waitForBlueCircleDisappear();
        waitForVisibilityOf(driver.findElement(getLeftMenuOption(menuOption)));
        click(driver.findElement(getLeftMenuOption(menuOption)),"option " + menuOption + " from left menu");
    }
}
