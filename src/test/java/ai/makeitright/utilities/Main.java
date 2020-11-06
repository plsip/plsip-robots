package ai.makeitright.utilities;

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

    @BeforeSuite
    public void tearUp() throws JSONException {
        report = new Reporter(artifactsPath + System.getProperty("file.separator") + reportName);
        report.startTest("PF Cloud test");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            report.logFail(result.getThrowable().toString());
            report.logScreenShot(screenshotsPath);
            Methods.getWebScreenShot(driver);
            report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        }
    }

    @AfterSuite
    public void tearDown(ITestContext context) throws JSONException, IOException, Exception {
        if (driver != null)
            driver.quit();
        report.closeRaport();
    }
}
