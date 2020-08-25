package ai.makeitright.tests.slack;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Methods;
import ai.makeitright.utilities.slack.*;
import ai.makeitright.utilities.slack.attachment.Attachment;
import ai.makeitright.utilities.slack.attachment.AttachmentBuilder;
import ai.makeitright.utilities.slack.attachment.AttachmentField;
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
        previousResultString = System.getProperty("previousResult.result");
        taskName = System.getProperty("previousResult.taskname");

    }

    @Test
    public void sendSlackMessage() throws Exception {
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
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel("@Katarzyna Raczkowska")
                .setUsername("Automation Tests Message")
                .setText("Tests run on " + Methods.returnEnvironment(pfSignInUrl));
        messageBuilder.addAttachment(generateMessageAttachment(taskName,previousResultString));

//        for(int index = 1; index <= attachmentCount; index++) {
//            messageBuilder.addAttachment(generateMessageAttachment(jsonArray.getString(index),jsonArray.optString(index)));
//        }
//        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
//                .setTitle("Task: " + taskName)
//                .setText(previousResultString)
//                .setColor("#000000")
//                .addField(new AttachmentField("Task",taskName))
//                .setFooter("Created by " + SendSlackMessageTest.class.getSimpleName())
//                .build();
        //messageBuilder.addAttachment(generateMessageAttachment(1));
//        Message message = new MessageBuilder()
//                .setChannel("@Katarzyna Raczkowska")
//                .setUsername("AutomationTests")
//                .setText("Tests run on " + Methods.returnEnvironment(pfSignInUrl))
//                .setText("Task: " + taskName)
//                .setText(previousResultString)
//                .build();
//        Message message2 = new MessageBuilder()
//                .setChannel("@Katarzyna Raczkowska")
//                .setUsername()
        webhook.postMessageSynchronous(messageBuilder.build());
//
//                .setText(previousResultString)

//        for (int index = 1; index <= attachmentCount; index++) {
//            String argName = jsonArray.getString(index);
//            message.getText(jsonObject.getString(argName));
//                message.setText(jsonObject.get(jsonArray.get(index)))
////                    .setText(previousResultString)
//        }
//                .build();
//        webhook.postMessage(message);
    }

    public static Attachment generateMessageAttachment(String taskName, String result) {
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
                .setTitle("Task: " + taskName)
                .setText(result)
                .setColor("#000000")
                .setFooter("Created by " + SendSlackMessageTest.class.getSimpleName());

        return attachmentBuilder.build();
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname",taskName);
        obj.put("result",previousResultString);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
