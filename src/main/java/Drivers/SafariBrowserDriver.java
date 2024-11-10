package Drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.safari.SafariDriver;


public class SafariBrowserDriver extends AbstractBrowserDriver {


    @Override
    protected void initializeDriver() {
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();
    }
}