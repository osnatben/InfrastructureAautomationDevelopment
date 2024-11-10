package ReportManagement.config.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;


public class ReportPortalListener implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        // Code to log success to Report Portal
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Code to log failure to Report Portal
    }

    // Implement other methods if needed
}
