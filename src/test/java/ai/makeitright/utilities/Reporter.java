package ai.makeitright.utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reporter extends DriverConfig {
    protected ExtentReports extent;
    protected ExtentTest reporter;

    private final String fileName;
    private boolean loggingOn;
    private boolean logFailAndErrorOnly;
    private static List<String> listResult = new ArrayList<>();

    public Reporter(String reportPathFileName, String workspacePath, String serviceFileName) {
        this.extent = new ExtentReports(reportPathFileName, true);
        loggingOn = true;
        logFailAndErrorOnly = false;
        fileName = new StringBuilder()
                .append(workspacePath)
                .append(System.getProperty("file.separator"))
                .append(serviceFileName)
                .toString();
    }

    public Reporter(String reportPathFileName, String workspacePath) {
        this(reportPathFileName, workspacePath, "service.log");
    }

    public Reporter(String reportPathFileName) {
        this(reportPathFileName, System.getProperty("ARTIFACTS_PATH"));
    }

    public String clearHtml(String msg) {
        if (msg != null) {
            return msg
                    .replaceAll("<br>|<p (.*?)>|<div(.*?)",
                            "\n")
                    .replaceAll("</p>|</div>|<font(.*?)>|</font>|<i>|</i>|<b>|</b>|<strong>|</strong>|<pre>|</pre>",
                            "");
        }
        return msg;
    }

    public void closeRaport() {
        extent.endTest(reporter);
        extent.flush();
    }

    public String exceptionHandler(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        writeTechnicalLog("[ERROR] :  " + sw.toString());
        logFail(e.getMessage());
        return sw.toString();
    }

    public ExtentTest startTest(String testName) {
        reporter = this.extent.startTest(testName);
        return reporter;
    }

    public void loadConfig(String fileName) {
        this.extent.loadConfig(new File(fileName));
    }

    public String logInfo(String msg) {
        if (loggingOn && !logFailAndErrorOnly) {
            listResult.add(clearHtml(msg));
            if (reporter != null) {
                reporter.log(LogStatus.INFO, msg);
            }
            writeTechnicalLog("[INFO] :  " + msg);
            System.out.println(msg);
        }
        return msg;
    }

    public String logPass(String msg) {
        if (loggingOn && !logFailAndErrorOnly) {
            listResult.add("[PASS]" + clearHtml(msg));
//            logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
            if (reporter != null) {
                reporter.log(LogStatus.PASS, msg);
            }
            writeTechnicalLog("[PASS] :  " + msg);
            System.out.println(msg);
        }
        return msg;
    }

    public String logFail(String msg) {
        if (loggingOn) {
            listResult.add("[FAIL]" + clearHtml(msg));
            logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
            if (reporter != null) {
                reporter.log(LogStatus.FAIL, msg);
            }
            writeTechnicalLog("[FAIL] :  " + msg);
            System.out.println(msg);
        }
        logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        return msg;
    }

    public void logInfoWithScreenCapture(String base64) {
        reporter.log(LogStatus.INFO, reporter.addBase64ScreenShot("data:image/png;base64," + base64));
    }

    public void logScreenCapture(String imgDataB64, LogStatus status) {
        if (reporter != null) {
            reporter.log(status, reporter.addBase64ScreenShot("data:image/png;base64," + imgDataB64));
        }
    }

    public void logScreenShot(String imagePath) {
        logScreenShot(imagePath, LogStatus.INFO);
    }

    public void logScreenShot(String imagePath, LogStatus status) {
        if (reporter != null) {
            reporter.log(status, reporter.addScreenCapture(imagePath));
        }
    }

    public String logTechnicalFail(String msg) {
        if (loggingOn) {
            listResult.add("[FAIL]" + clearHtml(msg));
            writeTechnicalLog("[FAIL] :  " + msg);
        }
        return msg;
    }

    public String writeTechnicalLog(String msg) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(Methods.getDateTime("yyyy-MM-dd HH:mm:ss  ") + clearHtml(msg));
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}

