package com.automationprompt.controller;

import com.automationprompt.dto.PromptRequest;
import com.automationprompt.dto.TemplateRequest;
import com.automationprompt.service.PromptService;
import com.automationprompt.service.TemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromptController.class)
public class PromptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromptService promptService;

    @MockBean
    private TemplateService templateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGeneratePrompt() throws Exception {
        PromptRequest request = new PromptRequest("web", "e2e", "cypress", "Login Feature", "javascript");
        request.setRequirements(Arrays.asList("Test data setup", "Error handling"));

        mockMvc.perform(post("/api/prompts/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTemplate() throws Exception {
        TemplateRequest request = new TemplateRequest("web-e2e");

        mockMvc.perform(post("/api/prompts/template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAvailableTemplates() throws Exception {
        when(templateService.getAvailableTemplates())
                .thenReturn(Set.of("web-e2e", "api", "unit", "mobile", "security", "performance"));

        mockMvc.perform(get("/api/prompts/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(6));
    }

    @Test
    public void testGetStats() throws Exception {
        when(promptService.getPromptsCount(any(Integer.class))).thenReturn(10L);

        mockMvc.perform(get("/api/prompts/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrompts").value(10));
    }
}
