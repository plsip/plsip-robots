package ai.makeitright.utilities;

import org.junit.After;
import org.junit.Before;

public class Main {
    public static Reporter report;
    public static String workspacePath = System.getProperty("ARTIFACTS_PATH");
    public final static String reportName = "Report.html";
    public static String serviceName = "";
    public String finalMsg = "";

    @Before
    public void tearUp() {
        report = new Reporter(workspacePath + System.getProperty("file.separator") + reportName);
        report.startTest(serviceName);
    }

    @After
    public void afterMethod() {
        report.closeRaport();
    }
}
