package ai.makeitright.pages.login;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrganizationSelectionPage extends BasePage {

    @FindBy(xpath = "//div/h1")
    private WebElement txtChooseOrganization;

    public OrganizationSelectionPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return waitForVisibilityOf(txtChooseOrganization);
    }

    public OrganizationSelectionPage clickSignInButton(String pfCompanyName) {
        click(driver.findElement(getBtnSignIn(pfCompanyName)), "button 'Sign in' for organization '" + pfCompanyName + "'");
        return this;
    }

    private By getBtnSignIn(String pfCompanyName) {
        return new By.ByXPath("//div[@class='organization-card-wrapper']//h2[text()='" + pfCompanyName + "']/parent::div/following-sibling::div//button");
    }
}
