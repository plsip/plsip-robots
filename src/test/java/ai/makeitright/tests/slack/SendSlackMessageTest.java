package ai.makeitright.tests.slack;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Methods;
import ai.makeitright.utilities.slack.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.methods.MethodsClient;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SendSlackMessageTest extends DriverConfig {

    private String pfSignInUrl;
    private String previousResultString;
    private String taskName;

    @Before
    public void before() {
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        previousResultString = System.getProperty("previousResult");
        taskName = System.getProperty("previousResult.taskname");

    }

    @Test
    public void sendSlackMessage() {
//        String hookUrl = "https://hooks.slack.com/services/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";
        String hookUrl = "https://hooks.slack.com/services/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";
        Webhook webhook = new Webhook(hookUrl);

//        JSONObject obj = new JSONObject();

//        obj.put("repositoryaddress", "dwedjffjef");

//        JsonObject jsonObject = new JsonParser().parse(previousResultString).getAsJsonObject();
//        int attachmentCount = jsonObject.size();
//        JSONObject jsonObject = new JSONObject().getJSONObject(previousResultString);
//        JSONArray jsonArray = jsonObject.names();
//        int attachmentCount = jsonObject.length();
        Message message = new MessageBuilder()
                .setChannel("@Katarzyna Raczkowska")
                .setUsername("AutomationTests")
                .setText("Tests run on " + Methods.returnEnvironment(pfSignInUrl))
                .setText(previousResultString)
                .build();
//        for (int index = 1; index <= attachmentCount; index++) {
//            String argName = jsonArray.getString(index);
//            message.getText(jsonObject.getString(argName));
//                message.setText(jsonObject.get(jsonArray.get(index)))
////                    .setText(previousResultString)
//        }
//                .build();
//        webhook.postMessage(message);
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskName);
        obj.put("result",previousResultString);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
