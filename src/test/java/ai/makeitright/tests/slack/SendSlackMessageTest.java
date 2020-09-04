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
    private String taskName;
    private String pfSignInUrl;

    @Before
    public void before() {
        channel = System.getProperty("inputParameters.channel");
        hookUrl = System.getProperty("secretParameters.hookUrl");
        taskName = System.getProperty("previousResult.taskname");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
    }

    @Test
    public void sendSlackMessage() throws Exception {
        Webhook webhook = new Webhook(hookUrl);
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel(channel)
                .setUsername("Automation Tests Message")
                .setText("Success on " + Methods.returnEnvironment(pfSignInUrl));
        messageBuilder.addAttachment(generateMessageAttachment(taskName));
        webhook.postMessageSynchronous(messageBuilder.build());

    }

    public static Attachment generateMessageAttachment(String taskName) {
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
                .setTitle("Task: " + taskName)
                .setColor("#228b22")
                .setFooter("Created by " + SendSlackMessageTest.class.getSimpleName());

        return attachmentBuilder.build();
    }

}
