package ai.makeitright.utilities;

import org.json.JSONException;
import org.testng.IConfigurationListener;
import org.testng.IConfigurationListener2;
import org.testng.ITestResult;

import java.io.IOException;

public class ConfigurationListener implements IConfigurationListener {
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        // TODO
    }


    @Override
    public void onConfigurationFailure(ITestResult itr) {
        try {
            if (Main.driver != null) {
                Methods.getWebScreenShot(Main.driver);
                Main.driver.quit();
            }
            Main.report.logTechnicalFail(itr.getThrowable().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
