package com.automationprompt.service;

import com.automationprompt.dto.PromptRequest;
import com.automationprompt.dto.PromptResponse;
import com.automationprompt.entity.PromptHistory;
import com.automationprompt.repository.PromptHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromptService {

    @Autowired
    private PromptHistoryRepository promptHistoryRepository;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private GeminiService geminiService;

    public PromptResponse generatePrompt(PromptRequest request) {
        String aiResponse = geminiService.getAiResponse(request.getPrompt());
        savePromptHistory(request, aiResponse, "gemini-pro");
        return new PromptResponse(aiResponse, "gemini-pro");
    }

    public PromptResponse getTemplate(String templateType) {
        String template = templateService.getTemplate(templateType);
        if (template != null) {
            PromptHistory history = new PromptHistory();
            history.setTemplateType(templateType);
            history.setGeneratedPrompt(template);
            history.setAppType("template");
            history.setTestType("template");
            history.setFramework("template");
            history.setFeatureName("Template: " + templateType);
            history.setProgrammingLanguage("template");
            promptHistoryRepository.save(history);

            return new PromptResponse(template, templateType);
        }
        return new PromptResponse("Template not found", templateType);
    }

    public List<PromptHistory> getRecentPrompts(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return promptHistoryRepository.findRecentPrompts(startDate);
    }

    public Long getPromptsCount(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return promptHistoryRepository.countPromptsGeneratedSince(startDate);
    }

    private void savePromptHistory(PromptRequest request, String generatedPrompt, String templateType) {
        PromptHistory history = new PromptHistory(
                request.getAppType(),
                request.getTestType(),
                request.getFramework(),
                request.getFeatureName(),
                request.getProgrammingLanguage(),
                generatedPrompt,
                templateType
        );
        promptHistoryRepository.save(history);
    }

    private String getTechnicalSpecifications(String testType) {
        switch (testType.toLowerCase()) {
            case "unit":
                return "- Follow AAA pattern (Arrange, Act, Assert)\n" +
                        "- Achieve high code coverage (90%+)\n" +
                        "- Include mock/stub implementations for dependencies\n" +
                        "- Test edge cases and error conditions\n" +
                        "- Use descriptive test names and organize in test suites\n";

            case "integration":
                return "- Test component interactions and data flow\n" +
                        "- Verify API integrations and database operations\n" +
                        "- Include setup and teardown for test environment\n" +
                        "- Validate error handling between components\n" +
                        "- Test configuration and dependency injection\n";

            case "e2e":
                return "- Implement page object model pattern\n" +
                        "- Use proper wait strategies and element selectors\n" +
                        "- Include cross-browser compatibility testing\n" +
                        "- Add screenshot capture on test failures\n" +
                        "- Validate complete user workflows\n";

            case "api":
                return "- Test all HTTP methods (GET, POST, PUT, DELETE)\n" +
                        "- Validate request/response schemas\n" +
                        "- Include authentication and authorization tests\n" +
                        "- Test error handling and status codes\n" +
                        "- Verify data persistence and state changes\n";

            case "performance":
                return "- Define load patterns and user scenarios\n" +
                        "- Set performance benchmarks and SLAs\n" +
                        "- Monitor resource utilization metrics\n" +
                        "- Include ramp-up and ramp-down strategies\n" +
                        "- Generate detailed performance reports\n";

            case "security":
                return "- Test authentication and session management\n" +
                        "- Validate input sanitization and XSS protection\n" +
                        "- Check for SQL injection vulnerabilities\n" +
                        "- Verify access control and authorization\n" +
                        "- Include OWASP Top 10 security tests\n";

            default:
                return "- Follow testing best practices for " + testType + "\n" +
                        "- Include comprehensive test coverage\n" +
                        "- Implement proper assertions and validations\n";
        }
    }

    private String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
