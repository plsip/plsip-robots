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
        System.out.println("CHeck at the beginning if there is something in directory");
        System.out.println("&&&&&& BEFORE TEST START KASIA &&&&&&&&");
        System.out.println("Check contains of folder: " + Main.artifactsPath);
        try {
            File folder = new File(Main.artifactsPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("&&& File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            System.out.println("&&&&&& BEFORE ETST END KASIA &&&&&&&&");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
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
