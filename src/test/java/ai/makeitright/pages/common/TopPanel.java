package ai.makeitright.pages.common;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopPanel extends BasePage {

    @FindBy(xpath = "//span[@class='Polaris-TopBar-UserMenu__Details']/p[1]")
    private WebElement txtTopPanelCreatedBy_Value;

    public TopPanel(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return txtTopPanelCreatedBy_Value.isDisplayed();
    }

    public String getCreatedBy() {
        return txtTopPanelCreatedBy_Value.getText();
    }
}
