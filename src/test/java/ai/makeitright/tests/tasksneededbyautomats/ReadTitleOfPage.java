package ai.makeitright.tests.tasksneededbyautomats;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

public class ReadTitleOfPage extends DriverConfig {

    private String channel;
    private String expectedTitle;
    private String slackFlag;
    private String title;
    private String url;

    @BeforeTest
    public void before() {
        Main.channel = this.channel;
        expectedTitle = System.getProperty("inputParameters.expectedTitle");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        url = System.getProperty("inputParameters.url");
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
