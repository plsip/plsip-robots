package ai.makeitright.pages.common;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopPanel extends BasePage {

    @FindBy(xpath = "//button[@class='Polaris-TopBar-Menu__Activator']")
    private WebElement btnTopMenu;

    @FindBy(xpath = "//li[2]//button[@class='Polaris-ActionList__Item']")
    private WebElement lnkLogOut;

    @FindBy(xpath = "//span[@class='Polaris-TopBar-UserMenu__Details']/p[1]")
    private WebElement txtTopPanelCreatedBy_Value;

    public TopPanel(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return txtTopPanelCreatedBy_Value.isDisplayed();
    }

    public TopPanel clickLogOutLink() {
        click(lnkLogOut,"'Log out'");
        return this;
    }

    public TopPanel clickTopPanelButton() {
        click(btnTopMenu, "'Top Menu' panel");
        return this;
    }

    public String getCreatedBy() {
        return txtTopPanelCreatedBy_Value.getText();
    }
}
