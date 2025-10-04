package com.automationprompt.controller;

import com.automationprompt.dto.PromptRequest;
import com.automationprompt.dto.PromptResponse;
import com.automationprompt.dto.TemplateRequest;
import com.automationprompt.entity.PromptHistory;
import com.automationprompt.service.GeminiService;
import com.automationprompt.service.PromptService;
import com.automationprompt.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/prompts")
@CrossOrigin(origins = "*")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate")
    public ResponseEntity<PromptResponse> generatePrompt(@Valid @RequestBody PromptRequest request) {
        try {
            // Get the formatted prompt from the request
            String formattedPrompt = request.getPrompt();
            // Get AI response from Gemini
            String aiResponse = geminiService.getAiResponse(formattedPrompt);
            // Save to history and return response
            PromptResponse response = new PromptResponse(aiResponse, "gemini-pro");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new PromptResponse("Error generating prompt: " + e.getMessage(), "error"));
        }
    }

    @PostMapping("/template")
    public ResponseEntity<PromptResponse> getTemplate(@Valid @RequestBody TemplateRequest request) {
        try {
            PromptResponse response = promptService.getTemplate(request.getTemplateType());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new PromptResponse("Error retrieving template: " + e.getMessage(), "error"));
        }
    }

    @GetMapping("/templates")
    public ResponseEntity<Set<String>> getAvailableTemplates() {
        Set<String> templates = templateService.getAvailableTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PromptHistory>> getRecentPrompts(
            @RequestParam(defaultValue = "7") int days) {
        List<PromptHistory> history = promptService.getRecentPrompts(days);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(
            @RequestParam(defaultValue = "7") int days) {
        Long count = promptService.getPromptsCount(days);
        List<PromptHistory> recent = promptService.getRecentPrompts(days);

        Map<String, Object> stats = Map.of(
                "totalPrompts", count,
                "recentPrompts", recent.size(),
                "period", days + " days"
        );

        return ResponseEntity.ok(stats);
    }
}
