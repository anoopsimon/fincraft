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

    /**
     * Retrieves a configuration value.
     * 1. Checks in the properties file.
     * 2. Checks in environment variables.
     * 3. Returns the default value if neither exists.
     *
     * @param key          The key to lookup.
     * @param defaultValue The default value to use if no value is found.
     * @return The resolved value.
     */
    public static String getConfig(String key, String defaultValue) {
        return Optional.ofNullable(properties.getProperty(key)) // Check properties file
                .orElseGet(() -> System.getenv(key) != null ? System.getenv(key) : defaultValue); // Check env or default
    }
}
