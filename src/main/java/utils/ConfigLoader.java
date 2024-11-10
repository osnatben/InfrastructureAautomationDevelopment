package utils;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = null;
    private static long lastModified = 0;

    public static Properties getProperties() {

        File configFile = new File(ConfigLoader.class.getClassLoader().getResource("config.properties").getFile());
        long currentLastModified = configFile.lastModified();

        if (properties == null || currentLastModified > lastModified) {
            properties = new Properties();
            try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (inputStream == null) {
                    throw new FileNotFoundException("config.properties not found in classpath");
                }
                properties.load(inputStream);
                lastModified = currentLastModified;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to load configuration file", e);
            }
        }
        return properties;
    }
}
