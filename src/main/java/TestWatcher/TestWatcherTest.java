package TestWatcher;

import Extentions.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import utils.WebDriverUtils;

import java.util.Optional;

public class TestWatcherTest extends Extension implements org.junit.jupiter.api.extension.TestWatcher {

    private WebDriverUtils webDriverUtils;

    public TestWatcherTest() {
    }

    public TestWatcherTest(WebDriverUtils webDriverUtils) {
        this.webDriverUtils = webDriverUtils;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Extension.writeToLog("Test '" + context.getDisplayName() + "' was successful.");  // קריאה לפונקציה סטטית
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Extension.writeToLog("Test '" + context.getDisplayName() + "' failed. Reason: " + cause.getMessage());  // קריאה לפונקציה סטטית

        String screenshotName = context.getDisplayName() + "_" + cause.getClass().getSimpleName();

        if (webDriverUtils == null) {
            webDriverUtils = new WebDriverUtils(Extension.getDriver());
        }

        webDriverUtils.takeScreenshot(screenshotName);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        Extension.writeToLog("Test '" + context.getDisplayName() + "' was aborted. Reason: " + cause.getMessage());  // קריאה לפונקציה סטטית
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        Extension.writeToLog("Test '" + context.getDisplayName() + "' was disabled. Reason: " + reason.orElse("No reason provided"));  // קריאה לפונקציה סטטית
    }
}
