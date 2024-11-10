package Extentions;

import Drivers.AbstractBrowserDriver;
import Drivers.BrowserFactory;
import Drivers.BrowserType;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import utils.ConfigLoader;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Extension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static Logger logger = Logger.getLogger("TestLogger");
    private static FileHandler fileHandler;
    private static boolean isRun = false;
    private static WebDriver driver; // דרייבר סטטי לכל הטסטים

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!isRun) {
            context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("StartTests", this);

            try {
                fileHandler = new FileHandler("test.log", true);
                logger.addHandler(fileHandler);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);

                initializeDriverFromProperties();

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize logging or driver", e);
            }
            isRun = true;
        }
    }

    private void initializeDriverFromProperties() throws IOException {
        Properties properties = ConfigLoader.getProperties(); // קריאה מתוך ConfigLoader

        String browserType = properties.getProperty("driver", "CHROME").toUpperCase();  // ברירת מחדל ל-CHROME
        BrowserType type = BrowserType.valueOf(browserType);

        AbstractBrowserDriver browserDriver = BrowserFactory.getBrowserDriver(type);
        driver = browserDriver.getDriver();

        String url = properties.getProperty("url");
        if (url != null) {
            driver.get(url);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Override
    public void close() throws Throwable {
        if (fileHandler != null) {
            fileHandler.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public static void writeToLog(String info) {
        if (logger != null) {
            logger.info(info);
        }
    }
}
