package ai.makeitright.tests.sometests.jsonarray;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestWithJsonArrayInOutput extends DriverConfig {

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
        Assert.assertEquals(title, expectedTitle, "The title of page is not like expected!");

    }


    @AfterTest
    public void prepareJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("title", title);
        jsonObject2.put("tytuł", title);
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        System.setProperty("output", jsonArray.toString());
    }

}
