package ai.makeitright.pages.settings;

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
        return table.isDisplayed();
    }

    @FindBy(xpath = "//li[@title='Next Page']/button[not(@disabled)]")
    private WebElement btnRightArrowPagination;

    @FindBy(xpath = "//tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> tableRows;

    private WebDriverWait wait = new WebDriverWait(driver, 5);

    public DisplayedCodeRepositoryAddress getAllRepositoriesAddressesRowData(final String repositoryAddress) {
        DisplayedCodeRepositoryAddress displayedCodeRepositoryAddress;
        do {
//            waitForVisibilityOf(tableRows.get(0));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));
            if (tableRows.size() > 0) {
                for (WebElement row : tableRows) {
                    String address = row.findElement(By.xpath(".//td//div//input")).getAttribute("value");
                    if (address.equals(repositoryAddress)) {
                        displayedCodeRepositoryAddress = new DisplayedCodeRepositoryAddress()
                                .setAddress(address);
                        Main.report.logPass("The repository address " + repositoryAddress + " was found on the platform's 'Repository Addresses' list");
                        return displayedCodeRepositoryAddress;
                    }

                }
                try {
                    btnRightArrowPagination.isDisplayed();
                    click(btnRightArrowPagination, "button with right arrow to go to the next page");
                } catch (Exception e) {
                    Main.report.logFail("There was no repository address '" + repositoryAddress + "'");
                    return null;
                }
            }
            else {
                Main.report.logInfo("There was no repository address '" + repositoryAddress + "'");
                return null;
            }
        } while(true);
    }

    public WebElement getDesirableRow(final String repositoryAddress) {
        do {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));
            if (tableRows.size() > 0) {
                for (WebElement row : tableRows) {
                    String address = row.findElement(By.xpath(".//td//div//input")).getAttribute("value");
                    if (address.equals(repositoryAddress)) {
                        Main.report.logPass("The repository address " + repositoryAddress + " was found on the platform's 'Repository Addresses' list");
                        return row;
                    }
                }
                try {
                    btnRightArrowPagination.isDisplayed();
                    click(btnRightArrowPagination, "button with right arrow to go to the next page");
                } catch (Exception e) {
                    Main.report.logFail("There was no repository address '" + repositoryAddress + "'");
                    return null;
                }
            }
            else {
                Main.report.logInfo("There was no repository address '" + repositoryAddress + "'");
                return null;
            }
        } while(true);
    }
}
