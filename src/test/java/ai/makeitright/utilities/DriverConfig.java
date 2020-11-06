package ai.makeitright.utilities;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

public class DriverConfig extends Main {

//    WebDriverWait wait = new WebDriverWait(Main.driver, 15);

    @BeforeSuite
    public static void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1400,600");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--single-process");
        options.addArguments("--use-gl=swiftshader");
        options.addArguments("--no-zygote");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

}
