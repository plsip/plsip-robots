package ai.makeitright.utilities;

import ai.makeitright.utilities.slack.SlackHandle;
import org.testng.IConfigurationListener;
import org.testng.ITestResult;

public class ConfigurationListener implements IConfigurationListener {
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
    }


    @Override
    public void onConfigurationFailure(ITestResult itr) {
        try {
            if (Main.driver != null) {
                Methods.getWebScreenShot(Main.driver);
                Main.driver.quit();
            }
            Main.report.logTechnicalFail(itr.getThrowable().toString());
            SlackHandle.sendFailedSlackMessage(Main.hookUrl, Main.channel, Main.pfSignInUrl, Main.taskname);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onConfigurationSkip(ITestResult itr) {
        // TODO
    }


    @Override
    public void beforeConfiguration(ITestResult tr) {
        // TODO
    }

}
