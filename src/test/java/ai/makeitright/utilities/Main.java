package ai.makeitright.utilities;

import ai.makeitright.utilities.slack.SlackHandle;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;

@Listeners({ConfigurationListener.class})
public abstract class Main {

    public static WebDriver driver;
    public static Reporter report;
    public static String artifactsPath = System.getProperty("ARTIFACTS_PATH");
    public static String screenshotsPath = System.getProperty("SCREENSHOTS_PATH");
    public final static String reportName = "Report.html";
    public static WebDriverWait wait;

    public static String hookUrl;
    public static String channel;
    public static String pfSignInUrl;
    public static String slackFlag;
    public static String taskname;

    @BeforeSuite
    public void tearUp() throws JSONException {
        report = new Reporter(artifactsPath + System.getProperty("file.separator") + reportName);
        report.startTest("PF Cloud test");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            report.logFail(result.getThrowable().toString());
            report.logScreenShot(screenshotsPath);
            Methods.getWebScreenShot(driver);
            report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
            SlackHandle.sendFailedSlackMessage(Main.hookUrl, Main.channel, Main.pfSignInUrl, Main.taskname);
        } else if(result.getStatus() == ITestResult.SUCCESS && Main.slackFlag.equals("true")) {
            SlackHandle.sendSuccessSlackMessage(Main.hookUrl, Main.channel, Main.pfSignInUrl, Main.taskname);
        }
    }

    @AfterSuite
    public void tearDown(ITestContext context) {
        if (driver != null)
            driver.quit();
        report.closeRaport();
    }
}
