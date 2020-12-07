package ai.makeitright.pages.jobs;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class JobsDetailsTasksTable extends BasePage {

    @FindBy(xpath = "//tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> tableRows;

    public JobsDetailsTasksTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")));
        return table.isDisplayed();
    }

    public DisplayedTasks getTasksFirstRowData() {
        WebElement row = tableRows.get(0);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        DisplayedTasks displayedTasks = new DisplayedTasks()
                .setLnkName(row.findElement(By.xpath("./th/a")))
                .setName(row.findElement(By.xpath("./th/a")).getText())
                .setStatus(rowColumns.get(STATUS).findElement(By.xpath("./div/span")).getText())
                .setUsedCommit(rowColumns.get(USEDCOMMIT).findElement(By.xpath("./b")).getText())
                .setEndDate(rowColumns.get(ENDDATE).getText())
                .setBtnShowResults(rowColumns.get(BTNSHOWRESULTS).findElement(By.xpath("./button")));
        return displayedTasks;
    }


    private final int STATUS = 0;
    private final int USEDCOMMIT = 1;
    private final int ENDDATE = 2;
    private final int BTNSHOWRESULTS = 3;
}
