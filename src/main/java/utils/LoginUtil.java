package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginUtil {

    private WebDriver driver;

    public LoginUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Perform login action with specified username and password.
     *
     * @param usernameLocatorType      The type of locator for the username field (ID or CLASS)
     * @param usernameLocatorValue     The value of the username locator
     * @param username                 The username to enter
     * @param passwordLocatorType      The type of locator for the password field (ID or CLASS)
     * @param passwordLocatorValue     The value of the password locator
     * @param password                 The password to enter
     * @param submitButtonLocatorType  The type of locator for the submit button (ID or CLASS)
     * @param submitButtonLocatorValue The value of the submit button locator
     */
    public void performLogin(String usernameLocatorType, String usernameLocatorValue, String username,
                             String passwordLocatorType, String passwordLocatorValue, String password,
                             String submitButtonLocatorType, String submitButtonLocatorValue) {

        // Find the username field
        By usernameLocator = getLocator(usernameLocatorType, usernameLocatorValue);
        WebElement usernameField = driver.findElement(usernameLocator);
        usernameField.sendKeys(username);

        // Find the password field
        By passwordLocator = getLocator(passwordLocatorType, passwordLocatorValue);
        WebElement passwordField = driver.findElement(passwordLocator);
        passwordField.sendKeys(password);

        // Find and click the submit button
        By submitButtonLocator = getLocator(submitButtonLocatorType, submitButtonLocatorValue);
        WebElement submitButton = driver.findElement(submitButtonLocator);
        submitButton.click();
    }

    private By getLocator(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "class":
                return By.className(locatorValue);
            case "cssSelector":
                return By.cssSelector(locatorValue);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }


}
