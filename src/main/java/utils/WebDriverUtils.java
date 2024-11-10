package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for common WebDriver operations.
 * Provides methods for element interaction, waiting, and handling browser actions.
 */
public class WebDriverUtils {

    private WebDriver driver;

    /**
     * Constructor to initialize WebDriverUtils with a WebDriver instance.
     *
     * @param driver the WebDriver instance
     */
    public WebDriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    // === Waiting Methods ===

    /**
     * Waits for an element to be visible on the page.
     *
     * @param locator the locator of the element to wait for
     * @param timeout the maximum wait time in seconds
     * @return the visible WebElement
     */
    public WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable.
     *
     * @param locator the locator of the element to wait for
     * @param timeout the maximum wait time in seconds
     * @return the clickable WebElement
     */
    public WebElement waitForElementToBeClickable(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for all elements located by the given locator to be visible.
     *
     * @param locator the locator of the elements to wait for
     * @param timeout the maximum wait time in seconds
     * @return a list of visible WebElements
     */
    public List<WebElement> waitForVisibilityOfAllElements(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Waits for the page to load completely.
     */
    public void waitForPageToLoad() {
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    // === Element Interaction Methods ===

    /**
     * Finds an element on the page.
     *
     * @param locator the locator of the element to find
     * @return the WebElement found
     */
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Sends keys to an input field after clearing it.
     *
     * @param locator the locator of the input field
     * @param text    the text to send
     */
    public void sendKeys(By locator, String text) {
        WebElement element = waitForElement(locator, 10);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clicks on an element.
     *
     * @param locator the locator of the element to click
     */
    public void clickElement(By locator) {
        WebElement element = waitForElement(locator, 10);
        element.click();
    }

    /**
     * Gets the visible text of an element.
     *
     * @param locator the locator of the element
     * @return the text of the element
     */
    public String getText(By locator) {
        WebElement element = waitForElement(locator, 10);
        return element.getText();
    }

    /**
     * Gets the value of a specified attribute from an element.
     *
     * @param locator   the locator of the element
     * @param attribute the attribute name
     * @return the value of the attribute
     */
    public String getAttribute(By locator, String attribute) {
        WebElement element = waitForElement(locator, 10);
        return element.getAttribute(attribute);
    }

    /**
     * Clears the content of an input field.
     *
     * @param locator the locator of the input field
     */
    public void clearField(By locator) {
        WebElement element = waitForElement(locator, 10);
        element.clear();
    }

    // === Dropdown Methods ===

    /**
     * Selects a value from a dropdown list.
     *
     * @param locator the locator of the dropdown
     * @param value   the visible text of the option to select
     */
    public void selectFromDropdown(By locator, String value) {
        WebElement dropdown = waitForElement(locator, 10);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    // === Scrolling and Hovering Methods ===

    /**
     * Scrolls the page until the specified element is in view.
     *
     * @param locator the locator of the element to scroll to
     */
    public void scrollToElement(By locator) {
        WebElement element = waitForElement(locator, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Hovers over a specified element.
     *
     * @param locator the locator of the element to hover over
     */
    public void hoverOverElement(By locator) {
        WebElement element = waitForElement(locator, 10);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    // === Frame Handling Methods ===

    /**
     * Switches to a specified frame.
     *
     * @param locator the locator of the frame
     */
    public void switchToFrame(By locator) {
        WebElement frame = waitForElement(locator, 10);
        driver.switchTo().frame(frame);
    }

    /**
     * Switches back to the default content.
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // === Browser Handling Methods ===

    /**
     * Closes the browser and ends the WebDriver session.
     */
    public void closeBrowser() {
        driver.quit();
    }

    // === Screenshot Method ===

    /**
     * Takes a screenshot and saves it to the specified location.
     *
     * @param fileName the name of the file to save the screenshot as
     */
    @Attachment(value = "Screenshot",type = "image/png")
    public void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = Paths.get("./screenshots", fileName + ".png");
            Files.copy(screenshot.toPath(), destination);
        } catch (IOException | ClassCastException e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
        }
    }

    // === Element Display Check ===

    /**
     * Checks if an element is displayed on the page.
     *
     * @param locator the locator of the element
     * @return true if the element is displayed, false otherwise
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false; // האלמנט לא נמצא
        }
    }
}
