package Common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

/**
 * Created by drygoi on 03.06.17.
 */
public class DriverPage {

    private static WebDriver driver;

    public static WebDriver getDriver() throws IOException {
        if (driver == null) {
            String driverType = Props.getProps().getProperty("driver_type").toString();

            switch (driverType) {
                case "firefox":
                    //todo тут менять путь при запуске на другой машине(выставлена настройка для linux машины)
                    System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");
//                    System.setProperty("webdriver.firefox.marionette", "src/geckodriver.exe");
//                    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//                    capabilities.setCapability("marionette", true);
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    break;
                case "chrome":
                    //todo тут менять путь при запуске на другой машине(выставлена настройка для windows)
                    System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    break;
                default:
                    throw new Error("Such Browser is not supported yet: " + driverType);
            }

        }
        return driver;
    }


}
