package typesWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ClickableWait implements WaitStrategy {
    private WebDriver driver;
    private WebDriverWait wait;

    public ClickableWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, (long) timeout);
    }

    @Override
    public WebElement applyWait(By locator) {

        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
