package Drivers;

import io.github.bonigarcia.wdm.WebDriverManager;


public class EdgeBrowserDriver  extends AbstractBrowserDriver {


    @Override
    protected void initializeDriver() {
        WebDriverManager.edgedriver().setup();
        driver = new org.openqa.selenium.edge.EdgeDriver();
    }
}