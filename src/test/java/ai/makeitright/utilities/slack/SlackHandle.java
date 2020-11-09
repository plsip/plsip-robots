package ai.makeitright.utilities.slack;

import ai.makeitright.tests.slack.SendSlackMessageTest;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import ai.makeitright.utilities.slack.attachment.Attachment;
import ai.makeitright.utilities.slack.attachment.AttachmentBuilder;

public class SlackHandle extends Main {

    public static void sendFailedSlackMessage(String hookUrl, String channel, String pfSignInUrl, String taskname) throws Exception {
        Webhook webhook = new Webhook(hookUrl);
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel(channel)
                .setUsername("Automation Tests Message")
                .setText("Failed on " + Methods.returnEnvironment(pfSignInUrl));
        messageBuilder.addAttachment(generateFailedMessageAttachment(taskname));
        webhook.postMessageSynchronous(messageBuilder.build());

    }

    public static void sendSuccessSlackMessage(String hookUrl, String channel, String pfSignInUrl, String taskname) throws Exception {
        Webhook webhook = new Webhook(hookUrl);
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel(channel)
                .setUsername("Automation Tests Message")
                .setText("Success on " + Methods.returnEnvironment(pfSignInUrl));
        messageBuilder.addAttachment(generateSuccessMessageAttachment(taskname));
        webhook.postMessageSynchronous(messageBuilder.build());

    }

    public static Attachment generateFailedMessageAttachment(String taskname) {
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
                .setTitle("Task: " + taskname)
                .setColor("#ff0000");

        return attachmentBuilder.build();
    }

    public static Attachment generateSuccessMessageAttachment(String taskname) {
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder()
                .setTitle("Task: " + taskname)
                .setColor("#228b22");

        return attachmentBuilder.build();
    }
}
