package typesWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StaleElementWait implements WaitStrategy {
    private WebDriver driver;
    private WebDriverWait wait;

    public StaleElementWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, (long) timeout); // כאן המרת ה-int ל-long
    }

    @Override
    public WebElement applyWait(By locator) {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));

        return driver.findElement(locator);
    }
}