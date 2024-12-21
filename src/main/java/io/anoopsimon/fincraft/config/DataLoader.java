package io.anoopsimon.fincraft.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting database initialization...");

        // Load the data.sql file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("config/data.sql").getInputStream(), StandardCharsets.UTF_8))) {

            // Read each SQL statement and execute with dynamic parameters
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("--")) { // Skip comments and empty lines
                    String baseQuery = line.trim();

                    // Dynamically modify and execute the query multiple times
                    for (int i = 1; i <= 10; i++) {
                        String dynamicQuery = modifyQueryWithParams(baseQuery, i);
                        jdbcTemplate.execute(dynamicQuery);
                    }
                }
            }
        }

        System.out.println("Database initialization complete!");
    }

    /**
     * Modifies a SQL query with dynamic parameters based on the index.
     *
     * @param query The original SQL query.
     * @param index The current loop index.
     * @return The dynamically updated SQL query.
     */
    private String modifyQueryWithParams(String query, int index) {
        return query
                .replace(":customerId", String.valueOf(index)) // Use generated IDs for customer references
                .replace(":name", "'Customer " + index + "'")
                .replace(":email", "'customer" + index + "@example.com'")
                .replace(":phoneNumber", "'123456789" + index + "'")
                .replace(":accountType", index % 2 == 0 ? "'DEPOSIT'" : "'TRANSACTION'")
                .replace(":balance", String.valueOf(1000.0 + index * 100))
                .replace(":creditLimit", String.valueOf(5000.0 + index * 500))
                .replace(":currentOutstanding", String.valueOf(index * 100.0));
    }
}
