import Drivers.AbstractBrowserDriver;
import Drivers.BrowserFactory;
import Drivers.BrowserType;

import java.io.File;
import Extentions.Extension;
import ReportManagement.config.Utils.ReportPortalUtils;
import ReportManagement.config.listeners.ReportPortalListener;
import TestWatcher.TestWatcherTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;
import utils.ConfigLoader;
import utils.LoginUtil;
import utils.SelfHealingElementFinder;
import utils.WebDriverUtils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({Extension.class, TestWatcherTest.class, ReportPortalListener.class})
public class TryTest {

    private static Extension extension;

    @BeforeEach
    public void startSession() {
        System.out.println("BeforeEach");
        extension = new Extension();
        extension.writeToLog("BeforeEach");
    }

    @BeforeAll
    public static void beforeTest() {

    }

    @AfterEach
    public void afterTest() {
        extension.writeToLog("AfterEach");

    }

    @AfterAll
    public static void endSession() {

    }


    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void testBrowser(BrowserType browserType) {
        AbstractBrowserDriver browserDriver = BrowserFactory.getBrowserDriver(browserType);
        browserDriver.getDriver();
        browserDriver.openUrl("https://www.example.com");

        browserDriver.quitDriver();
    }

    @Test
    public void testBrowser1() {
        WebDriver driver = Extension.getDriver();
        // המשך הבדיקות עם הדרייבר
        System.out.println("Page title is: " + driver.getTitle());
        // לוג דוגמה
        Extension.writeToLog("Running testExample");
    }


    @Test
    public void testScreenshot() {
        String urlScreenshot = ConfigLoader.getProperties().getProperty("urlScreenshot", "https://translate.google.com");

        WebDriver driver = Extension.getDriver();

        WebDriverUtils webDriverUtils = new WebDriverUtils(driver);

        File screenshotsDir = new File("./screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }
        try {
            driver.get(urlScreenshot);

            assertEquals("Google Translate", driver.getTitle());
        } catch (AssertionFailedError e) {
            webDriverUtils.takeScreenshot("testScreenshot");
            throw e;
        } catch (Exception e) {
            webDriverUtils.takeScreenshot("testScreenshotError");
            throw e;
        } finally {
            driver.quit();
        }
    }


    @Test
    public void testFindElementWithRetries() {

        SelfHealingElementFinder elementFinder;

        WebDriver driver = Extension.getDriver();

        elementFinder = new SelfHealingElementFinder(driver);

        driver.get(ConfigLoader.getProperties().getProperty("urlMizrahi"));
        By locator = By.id("testElementId");

        WebElement element = elementFinder.findElement(locator, strategy -> {
            return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
        });

        assertNotNull(element, "The element should be visible and not null");

        if (driver != null) {
            driver.quit();
        }
    }


    @Step("Fill Form")
    @Test
    public void testLoginForm() {
        WebDriver driver = Extension.getDriver();
        LoginUtil loginUtil = new LoginUtil(driver);

        driver.get(ConfigLoader.getProperties().getProperty("urlMizrahi"));

        loginUtil.performLogin(
                "id", "emailDesktopHeb", "Osnat", // פרמטרים לשם המשתמש
                "id", "passwordIDDesktopHEB", "1234", // פרמטרים לסיסמא
                "cssSelector", ".form-group.no-margin.mt-4.d-grid.gap-1" // פרמטרים לכפתור ההתחברות
        );
        driver.quit();
    }
}
