package com.livechat.backend;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
class BackendApplicationTests {

    private static final String DB_NAME = "livechat_test";
    private static final String DB_USER = "livechat";
    private static final String DB_PASSWORD = "livechat";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("resource")
    @Container
    static GenericContainer<?> postgres = new GenericContainer<>("postgres:16-alpine")
            .withEnv("POSTGRES_DB", DB_NAME)
            .withEnv("POSTGRES_USER", DB_USER)
            .withEnv("POSTGRES_PASSWORD", DB_PASSWORD)
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", BackendApplicationTests::buildJdbcUrl);
        registry.add("spring.datasource.username", () -> DB_USER);
        registry.add("spring.datasource.password", () -> DB_PASSWORD);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.flyway.url", BackendApplicationTests::buildJdbcUrl);
        registry.add("spring.flyway.user", () -> DB_USER);
        registry.add("spring.flyway.password", () -> DB_PASSWORD);
    }

    private static String buildJdbcUrl() {
        return "jdbc:postgresql://%s:%d/%s".formatted(postgres.getHost(), postgres.getMappedPort(5432), DB_NAME);
    }

    @Test
    void contextLoadsAndAppliesFlywayMigrations() {
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'users'",
                Integer.class);
        assertEquals(1, Objects.requireNonNull(tableCount), "Expected Flyway migration to create users table");
    }

}
