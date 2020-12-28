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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.GregorianCalendar;

public class Methods extends Main {

    public static String getDateTime(String format) {
        return new SimpleDateFormat(format).format(new GregorianCalendar().getTime());
    }

    public static String getFirstDayOfNextMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfNextMonth = currentDate.with(TemporalAdjusters.firstDayOfNextMonth());
        return firstDayOfNextMonth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getOrdinalIndicatorOfNextDay() {
        String[] suffixes =
                { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        "th", "st" };
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        int day = Integer.parseInt(formatter.format(currentDate.plusDays(1)));
        return day + suffixes[day];
    }

    public static String getNextDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return Integer.toString(currentDate.plusDays(1).getDayOfMonth());
    }

    public static String getDateOfNextDay(String format) {
        LocalDate currentDate = LocalDate.now();
        return DateTimeFormatter.ofPattern(format).format(currentDate.plusDays(1));
    }

    public static String getNameOfNextDay() {
        LocalDate currentDate = LocalDate.now();
        String output = currentDate.plusDays(1).getDayOfWeek().toString();
        return output.substring(0, 1) + output.substring(1).toLowerCase();
    }

    public static String getScreenShotAsBase64(WebDriver driver) {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BASE64);
    }

    public static String getWebScreenShot(WebDriver driver) throws IOException {
        return getWebScreenShot(driver, "screenshot" + getDateTime("yyyy-MM-dd_HH-mm-ss"));
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
