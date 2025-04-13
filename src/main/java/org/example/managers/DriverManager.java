package org.example.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.Assertions;

import static org.example.utils.PropConst.*;

public class DriverManager {
    private WebDriver driver;
    private static DriverManager INSTANCE = null;
    private final TestPropManager props = TestPropManager.getTestPropManager();

    private DriverManager() {}

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initDriver() {
        switch (props.getProperty(TYPE_BROWSER).toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup(); // Автоматическая настройка FirefoxDriver
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup(); // Автоматическая настройка ChromeDriver
                driver = new ChromeDriver();
                break;
            default:
                Assertions.fail("Типа браузера '" + props.getProperty(TYPE_BROWSER) + "' не существует во фреймворке");
        }
    }
}