package io.anoopsimon.fincraft.util;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ConfigUtil {

    private static Properties properties = new Properties();

    static {
        try {
            // Load properties file from src/test/resources
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load application.properties: " + e.getMessage());
        }
    }

    public static String getConfig(String key, String defaultValue) {
        // 1. Check system properties first
        String fromSystemProps = System.getProperty(key);
        if (fromSystemProps != null) {
            return fromSystemProps;
        }

        // 2. If not found in system properties, check application.properties
        String fromProperties = properties.getProperty(key);
        if (fromProperties != null) {
            return fromProperties;
        }

        // 3. Fallback to the default value
        return defaultValue;
    }

}
