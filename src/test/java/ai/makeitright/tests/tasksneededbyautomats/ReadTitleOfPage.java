package ai.makeitright.tests.tasksneededbyautomats;

import ai.makeitright.utilities.DriverConfig;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ReadTitleOfPage extends DriverConfig {

    private String expectedTitle;
    private String title;
    private String url;

    @BeforeTest
    public void before() {
        url = System.getProperty("inputParameters.url");
        expectedTitle = System.getProperty("inputParameters.expectedTitle");
    }

    @Test
    public void readTitleOfPage() {
        driver.get(url);
        title = driver.getTitle();
        Assert.assertEquals(title, expectedTitle, "The title of page is not like expected");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        System.setProperty("output", obj.toString());
    }
}
