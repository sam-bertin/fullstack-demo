package com.livechat.backend.auth.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livechat.backend.auth.dto.LoginRequest;
import com.livechat.backend.auth.dto.RegisterRequest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@SuppressWarnings("null")
class AuthControllerIntegrationTest {

        private static final String DB_NAME = "livechat_test";
        private static final String DB_USER = "livechat";
        private static final String DB_PASSWORD = "livechat";

    @SuppressWarnings("resource")
    @Container
        static GenericContainer<?> postgres = new GenericContainer<>("postgres:16-alpine")
                        .withEnv("POSTGRES_DB", DB_NAME)
                        .withEnv("POSTGRES_USER", DB_USER)
                        .withEnv("POSTGRES_PASSWORD", DB_PASSWORD)
                        .withExposedPorts(5432)
                        .waitingFor(Wait.forListeningPort());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", AuthControllerIntegrationTest::buildJdbcUrl);
                registry.add("spring.datasource.username", () -> DB_USER);
                registry.add("spring.datasource.password", () -> DB_PASSWORD);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
                registry.add("spring.flyway.url", AuthControllerIntegrationTest::buildJdbcUrl);
                registry.add("spring.flyway.user", () -> DB_USER);
                registry.add("spring.flyway.password", () -> DB_PASSWORD);
        }

        private static String buildJdbcUrl() {
                return "jdbc:postgresql://%s:%d/%s".formatted(postgres.getHost(), postgres.getMappedPort(5432), DB_NAME);
    }

    @Test
    void registerThenLoginShouldSucceed() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("flow@example.com", "flowuser", "password123");
        LoginRequest loginRequest = new LoginRequest("flow@example.com", "password123");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.email", is("flow@example.com")));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.authMode", is("PASSWORD_ONLY_TEMPORARY")));
    }

    @Test
    void loginShouldFailOnInvalidPassword() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("invalid@example.com", "invaliduser", "password123");
        LoginRequest invalidLogin = new LoginRequest("invalid@example.com", "wrongpass123");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidLogin)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errorCode", is("INVALID_CREDENTIALS")));
    }

    @Test
    void registerShouldFailWhenUsernameAlreadyExists() throws Exception {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        String duplicateUsername = "dupuser-" + suffix;

        RegisterRequest firstRegister = new RegisterRequest(
                "first-" + suffix + "@example.com",
                duplicateUsername,
                "password123"
        );
        RegisterRequest secondRegister = new RegisterRequest(
                "second-" + suffix + "@example.com",
                duplicateUsername,
                "password123"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstRegister)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondRegister)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errorCode", is("RESOURCE_CONFLICT")));
    }
}
