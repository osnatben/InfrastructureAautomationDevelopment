package ReportManagement.config.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportPortalUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReportPortalUtils.class);

    public static void logToReportPortal(String message) {
        // Implement logic to send logs to Report Portal
        logger.info(message);
    }
}