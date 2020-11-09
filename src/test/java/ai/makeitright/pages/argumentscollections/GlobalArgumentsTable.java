package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GlobalArgumentsTable extends BasePage {

    @FindBy(xpath = "//ul[@class='Polaris-ResourceList']")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//ul[@class='Polaris-ResourceList']/li")
    )
    private List<WebElement> tableRows;

    public GlobalArgumentsTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return table.isDisplayed();
    }

    public DisplayedGlobalArguments getGlobalArgumentsRowData(String globalArgumentsCollection) {
        DisplayedGlobalArguments displayedGlobalArguments;
        for (WebElement row : tableRows) {
            List<WebElement> rowColumns = row.findElements(By.xpath("./div/div/div/div/div/div"));
            if (rowColumns.get(COLLECTIONNAME).findElement(By.xpath("./span")).getText().equals(globalArgumentsCollection)) {
                displayedGlobalArguments = new DisplayedGlobalArguments()
                        .setCollectionName(globalArgumentsCollection)
                        .setBtnArgumentsCollectionName(rowColumns.get(COLLECTIONNAME).findElement(By.xpath("./span")))
                        .setAuthor(rowColumns.get(AUTHOR).getText().substring(8))
                        .setCreatedDate(rowColumns.get(CREATEDDATE).findElement(By.xpath("./div")).getText().substring(9))
                        .setBtnDelete(rowColumns.get(BTNDELETE).findElement(By.xpath("./div/button")));
                Main.report.logPass("Global arguments collection with name " + globalArgumentsCollection + " was found in table 'Global arguments'");
                return displayedGlobalArguments;
            }
        }
        Main.report.logInfo("There was no global arguments collection with name '" + globalArgumentsCollection + "' in table 'Global arguments'");
        return null;
    }

    private int COLLECTIONNAME = 0;
    private int AUTHOR = 1;
    private int CREATEDDATE = 2;
    private int BTNDELETE = 3;
}
