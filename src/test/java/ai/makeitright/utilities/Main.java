package ai.makeitright.utilities;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Main {
    public static Reporter report;
    public static String artifactsPath = System.getProperty("ARTIFACTS_PATH");
    public final static String reportName = "Report.html";
    public static String serviceName = "";
    public String finalMsg = "";

    @BeforeMethod
    public void tearUp() {
        report = new Reporter(artifactsPath + System.getProperty("file.separator") + reportName);
        report.startTest(serviceName);
    }

    @AfterMethod
    public void afterMethod() {
        report.closeRaport();
    }
}
