package Drivers;

import org.openqa.selenium.WebDriver;

public abstract class AbstractBrowserDriver {

    protected WebDriver driver;

    public WebDriver getDriver() {
        initializeDriver();
        driver.manage().window().maximize();
        return driver;
    }


    protected abstract void initializeDriver();

    public void openUrl(String url) {
        driver.get(url);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
