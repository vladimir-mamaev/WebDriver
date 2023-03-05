package singltoneDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static constant.Constants.IMPLICITLY_WAIT_TIMEOUT;


public class SingletonDriver {
    private SingletonDriver() {
    }

    private static WebDriver instance;

    public static WebDriver getDriver() {
        if (instance == null) {
            System.setProperty("webdriver.chrome.driver", "src/main/java/driver/chromedriver.exe");
            instance = new ChromeDriver();
            instance.manage().window().maximize();
            instance.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_TIMEOUT, TimeUnit.SECONDS);
        }
        return instance;
    }
}

