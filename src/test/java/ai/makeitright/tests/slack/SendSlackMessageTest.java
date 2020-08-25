package ai.makeitright.tests.slack;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.slack.*;
import com.slack.api.methods.MethodsClient;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class SendSlackMessageTest extends DriverConfig {

    private String previousResultString;

    @Before
    public void before() {
        previousResultString = System.getProperty("previousResult");
    }

    @Test
    public void sendSlackMessage() {
//        String hookUrl = "https://hooks.slack.com/services/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";
        String hookUrl = "https://hooks.slack.com/services/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";
        Webhook webhook = new Webhook(hookUrl);

//        JSONObject obj = new JSONObject();
//        obj.put("repositoryaddress", "dwedjffjef");


        Message message = new MessageBuilder()
                .setChannel("@Katarzyna Raczkowska")
                .setUsername("AutomationTests")
                .setText(previousResultString)
                .build();
        webhook.postMessage(message);
    }
}
