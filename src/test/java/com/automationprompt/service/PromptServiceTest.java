package com.automationprompt.service;

import com.automationprompt.dto.PromptRequest;
import com.automationprompt.dto.PromptResponse;
import com.automationprompt.repository.PromptHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromptServiceTest {

    @Mock
    private PromptHistoryRepository promptHistoryRepository;

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private PromptService promptService;

    private PromptRequest testRequest;

    @BeforeEach
    void setUp() {
        testRequest = new PromptRequest("web", "e2e", "cypress", "Login Feature", "javascript");
        testRequest.setFeatureDescription("User authentication system");
        testRequest.setRequirements(Arrays.asList("Test data setup", "Error handling"));
        testRequest.setScenarios("- Valid login\n- Invalid credentials\n- Logout functionality");
    }

    @Test
    void testGeneratePrompt() {
        PromptResponse response = promptService.generatePrompt(testRequest);

        assertNotNull(response);
        assertNotNull(response.getGeneratedPrompt());
        assertTrue(response.getGeneratedPrompt().contains("Login Feature"));
        assertTrue(response.getGeneratedPrompt().contains("cypress"));
        assertTrue(response.getGeneratedPrompt().contains("javascript"));
        assertEquals("custom", response.getTemplateType());
    }

    @Test
    void testGetTemplate() {
        String templateContent = "Mock template content";
        when(templateService.getTemplate("web-e2e")).thenReturn(templateContent);

        PromptResponse response = promptService.getTemplate("web-e2e");

        assertNotNull(response);
        assertEquals(templateContent, response.getGeneratedPrompt());
        assertEquals("web-e2e", response.getTemplateType());
    }

    @Test
    void testGetTemplateNotFound() {
        when(templateService.getTemplate("non-existent")).thenReturn(null);

        PromptResponse response = promptService.getTemplate("non-existent");

        assertNotNull(response);
        assertEquals("Template not found", response.getGeneratedPrompt());
        assertEquals("non-existent", response.getTemplateType());
    }
}
