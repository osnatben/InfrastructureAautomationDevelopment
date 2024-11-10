package utils;

import typesWait.WaitStrategy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class SelfHealingElementFinder {

    private WebDriver driver;
    private final int maxRetries = 3;

    public SelfHealingElementFinder(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By locator, WaitStrategy strategy) {
        return retryFindElement(locator, strategy, maxRetries);
    }

    private WebElement retryFindElement(By locator, WaitStrategy strategy, int retries) {
        int attempts = 0;
        WebElement element = null;

        while (attempts < retries) {
            try {
                log.info("Attempting to find element with locator: {}. Attempt: {}", locator, attempts + 1);

                element = strategy.applyWait(locator);

                log.info("Element found successfully on attempt: {}", attempts + 1);

                break;
            } catch (Exception e) {
                attempts++;

                log.error("Error finding element on attempt {}: {}. Full exception: ", attempts, e.getMessage(), e);
            }
        }

        if (element == null) {
            log.error("Unable to locate element after {} attempts with locator: {}", retries, locator);
            throw new RuntimeException("Unable to locate element after " + retries + " attempts.");
        }

        return element;
    }
}
