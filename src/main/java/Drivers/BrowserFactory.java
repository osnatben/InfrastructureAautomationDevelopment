package Drivers;

public class BrowserFactory {

    public static AbstractBrowserDriver getBrowserDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                return new ChromeBrowserDriver();
            case FIREFOX:
                return new FirefoxBrowserDriver();
            case EDGE:
                return new EdgeBrowserDriver();
//            case SAFARI:
//                return new SafariBrowserDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }
}
