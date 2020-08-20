package ai.makeitright.pages.settigns;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RepositoriesAddressesTable extends BasePage {
    public RepositoriesAddressesTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='Polaris-TextContainer Polaris-TextContainer--spacingLoose']")));
        return table.isDisplayed();
    }

    @FindBy(xpath = "//div[@class='Polaris-TextContainer Polaris-TextContainer--spacingLoose']")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//div[@class='Polaris-TextContainer Polaris-TextContainer--spacingLoose']/div")
    )
    private List<WebElement> tableRows;

    public DisplayedCodeRepositoryAddress getAllRepositoriesAddressesRowData(final String repositoryAddress) {
        DisplayedCodeRepositoryAddress displayedCodeRepositoryAddress;
        waitForVisibilityOf(tableRows.get(0));
        for (WebElement row : tableRows) {
            if(row.findElement(By.xpath(".//input")).getAttribute("value").equals(repositoryAddress)) {
                displayedCodeRepositoryAddress = new DisplayedCodeRepositoryAddress()
                        .setAddress(row.findElement(By.xpath(".//input")).getText());
                Main.report.logPass("The repository address " + repositoryAddress + " was found on the platform's 'Repository Addresses' list");
                return displayedCodeRepositoryAddress;
            }

        }
        Main.report.logFail("There was no repository address '" + repositoryAddress);
        return null;
    }
}
