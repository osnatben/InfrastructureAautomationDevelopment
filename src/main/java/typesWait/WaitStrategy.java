package typesWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface WaitStrategy {
    WebElement applyWait(By locator);

}
