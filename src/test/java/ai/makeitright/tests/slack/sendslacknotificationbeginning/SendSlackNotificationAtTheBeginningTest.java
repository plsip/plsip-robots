package ai.makeitright.tests.slack.sendslacknotificationbeginning;

import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Methods;
import ai.makeitright.utilities.slack.MessageBuilder;
import ai.makeitright.utilities.slack.Webhook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SendSlackNotificationAtTheBeginningTest extends DriverConfig {

    private String channel;
    private String hookUrl;
    private String pfSignInUrl;

    @BeforeTest
    public void before() {
        channel = System.getProperty("inputParameters.channel");
        hookUrl = System.getProperty("secretParameters.hookUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
    }

    @Test
    public void sendSlackNotification() throws Exception {
        Webhook webhook = new Webhook(hookUrl);
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel(channel)
                .setUsername("Automation Tests Message")
                .setText("Tests run on " + Methods.returnEnvironment(pfSignInUrl) + " \u2193");
        webhook.postMessageSynchronous(messageBuilder.build());
    }

}
