package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class GlobalArgumentsTable extends BasePage {

    public GlobalArgumentsTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        //return table.isDisplayed();
        return true;
    }
}
