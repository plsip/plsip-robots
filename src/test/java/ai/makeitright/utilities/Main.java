package ai.makeitright.utilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class Main {
    public static Reporter report;
    public static String workspacePath = System.getProperty("ARTIFACTS_PATH");
    public final static String reportName = "Report.html";
    public static String serviceName = "";
    public String finalMsg = "";

    @BeforeEach
    public void tearUp() {
        report = new Reporter(workspacePath + System.getProperty("file.separator") + reportName);
        report.startTest(serviceName);
    }

    @AfterEach
    public void afterMethod() {
        report.closeRaport();
    }
}
