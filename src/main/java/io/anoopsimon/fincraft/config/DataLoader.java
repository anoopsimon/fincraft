package io.anoopsimon.fincraft.config;

import io.anoopsimon.fincraft.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate; // To execute raw SQL
    private final UserRepository userRepository;

    public DataLoader(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Path to the SQL file
        Resource resource = new ClassPathResource("config/data.sql");

        // Read the SQL file content
        String sqlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));

        // Split SQL statements and execute each one
        String[] sqlStatements = sqlContent.split(";");
        for (String sql : sqlStatements) {
            if (!sql.trim().isEmpty()) {
                jdbcTemplate.execute(sql.trim());
            }
        }

        System.out.println("Sample users added to the database from file!");
    }
}
