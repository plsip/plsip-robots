package ai.makeitright.tests.slack.sendslacknotificationbeginning;

import ai.makeitright.utilities.DriverConfig;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

public class SendSlackNotificationAtTheBeginningTest extends DriverConfig {

    @Test
    public void sendSlackNotification() {

    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("result","ok");
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
