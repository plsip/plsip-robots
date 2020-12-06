package ai.makeitright.pages.users;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UsersTable extends BasePage {

    @FindBy(xpath = "//main//div/div[1]/ul")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//main//div/div[1]/ul/li")
    )
    private List<WebElement> tableRows;

    public UsersTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//main//div/div[1]/ul")));
        return table.isDisplayed();
    }

    public DisplayedUsers getUsersFirstRowData() {
        DisplayedUsers displayedUsers;
        WebElement row = tableRows.get(0);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        displayedUsers = new DisplayedUsers()
                .setSrcAvatarImage(row.findElement(By.xpath("./div/div/div/div/div/span/img")).getAttribute("src"))
                .setLnkUserDetails(row.findElement(By.xpath(".//p")))
                .setFirstLastName(row.findElement(By.xpath("./div/div/div/div[2]/div/div[1]/div/div/h2/span")).getText())
                .setEmail(row.findElement(By.xpath(".//p")).getText())
                .setRole(row.findElement(By.xpath("./div/div/div/div[2]/div/div[2]/div")).getText());
        return displayedUsers;
    }

}
