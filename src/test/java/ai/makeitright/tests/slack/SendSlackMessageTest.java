package ai.makeitright.tests.slack;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Methods;
import ai.makeitright.utilities.slack.*;
import ai.makeitright.utilities.slack.attachment.Attachment;
import ai.makeitright.utilities.slack.attachment.AttachmentBuilder;
import org.junit.Before;
import org.junit.Test;

public class SendSlackMessageTest extends DriverConfig {

    private String channel;
    private String hookUrl;
    private String pfSignInUrl;
    private String previousResultString;
    private String taskName;

    @Before
    public void before() {
        channel = System.getProperty("inputParameters.channel");
        hookUrl = System.getProperty("secretParameters.hookUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        previousResultString = System.getProperty("previousResult");
        taskName = System.getProperty("previousResult.taskname");
    }

    @Test
    public void sendSlackMessage() throws Exception {
        Webhook webhook = new Webhook(hookUrl);
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel(channel)
                .setUsername("Automation Tests Message")
                .setText("Tests run on " + Methods.returnEnvironment(pfSignInUrl));
        messageBuilder.addAttachment(generateMessageAttachment(taskName,previousResultString));
        webhook.postMessageSynchronous(messageBuilder.build());

    }

    public static Attachment generateMessageAttachment(String taskName, String result) {
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
                .setTitle("Task: " + taskName)
                .setText(result)
                .setColor("#000000")
                .setFooter("Created by " + SendSlackMessageTest.class.getSimpleName());

        return attachmentBuilder.build();
    }

}
