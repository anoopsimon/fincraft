package io.anoopsimon.fincraft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private static final Properties properties = new Properties();

    static {
        try {
            // Load properties file from src/test/resources (or src/main/resources if that’s your use case)
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties"));
            logger.info("Loaded application.properties successfully.");
        } catch (IOException e) {
            logger.error("Failed to load application.properties: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves a configuration value.
     * 1. Checks system properties.
     * 2. Checks the application.properties file.
     * 3. Returns the default value if neither is found.
     *
     * @param key          The key to lookup.
     * @param defaultValue The default value to use if no value is found.
     * @return The resolved value.
     */
    public static String getConfig(String key, String defaultValue) {
        // 1. Check system properties
        String systemProp = System.getProperty(key);
        if (systemProp != null) {
            logger.debug("Using system property for key='{}'", key);
            return systemProp;
        }

        // 2. Check application.properties
        String fileProp = properties.getProperty(key);
        if (fileProp != null) {
            logger.debug("Using application.properties value for key='{}'", key);
            return fileProp;
        }

        // 3. Fallback to default
        logger.debug("Using default value for key='{}': '{}'", key, defaultValue);
        return defaultValue;
    }
}
