package ReportManagement.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                throw new RuntimeException("application.properties file not found");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error loading properties file", ex);
        }
    }

    public static String getEndpoint() {
        return properties.getProperty("rp.endpoint");
    }

    public static String getUuid() {
        return properties.getProperty("rp.uuid");
    }

    public static String getProject() {
        return properties.getProperty("rp.project");
    }

    public static String getLaunch() {
        return properties.getProperty("rp.launch");
    }

    public static String getMode() {
        return properties.getProperty("rp.mode");
    }
}
