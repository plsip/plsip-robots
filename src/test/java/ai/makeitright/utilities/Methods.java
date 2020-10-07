package ai.makeitright.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.GregorianCalendar;

public class Methods extends Main {

    public static String getDateTime(String format) {
        return new SimpleDateFormat(format).format(new GregorianCalendar().getTime());
    }

    public static String getNextDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return Integer.toString(currentDate.plusDays(1).getDayOfMonth());
    }

    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.getHour() + ":" + currentTime.plusMinutes(1).getMinute();
    }

    public static String getScreenShotAsBase64(WebDriver driver) {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BASE64);
    }

    public static String getWebScreenShot(WebDriver driver) throws IOException {
        return getWebScreenShot(driver, "screenshot" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new GregorianCalendar().getTime()));
    }

    public static String getWebScreenShot(WebDriver driver, String strFilename) throws IOException {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        File srcFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        String filePath = Main.artifactsPath + System.getProperty("file.separator");
        String fileName = strFilename + ".png";
        FileUtils.copyFile(srcFile, new File(filePath + fileName));
        return fileName;
    }

    public static String returnEnvironment(String url) {
        if (url.contains("development")) {
            return "DEV";
        } else if (url.contains("staging")) {
            return "STAGING";
        } else {
            return null;
        }
    }
}
