package com.automationprompt.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class TemplateService {

    private final Map<String, String> templates;

    public TemplateService() {
        templates = new HashMap<>();
        initializeTemplates();
    }

    public String getTemplate(String templateType) {
        return templates.get(templateType);
    }

    public Set<String> getAvailableTemplates() {
        return templates.keySet();
    }

    private void initializeTemplates() {
        templates.put("web-e2e", getWebE2ETemplate());
        templates.put("api", getApiTemplate());
        templates.put("unit", getUnitTemplate());
        templates.put("mobile", getMobileTemplate());
        templates.put("security", getSecurityTemplate());
        templates.put("performance", getPerformanceTemplate());
    }

    private String getWebE2ETemplate() {
        return "Generate comprehensive end-to-end tests for a web application using Cypress framework.\n\n" +
                "Application Context:\n" +
                "- Web application: [Feature Name]\n" +
                "- Technology: [Tech stack]\n" +
                "- Framework: Cypress with JavaScript\n\n" +
                "Test Requirements:\n" +
                "1. Create tests for the complete user workflow\n" +
                "2. Include form validation testing\n" +
                "3. Test cross-browser compatibility scenarios\n" +
                "4. Implement page object model pattern\n" +
                "5. Add proper wait strategies and element selectors\n" +
                "6. Include screenshot capture on failures\n\n" +
                "Please generate complete test files with proper setup, teardown, and documentation.";
    }

    private String getApiTemplate() {
        return "Create comprehensive API test automation using [Framework] for [API Name].\n\n" +
                "API Context:\n" +
                "- API Type: REST API\n" +
                "- Base URL: [API endpoint]\n" +
                "- Authentication: [Auth method]\n\n" +
                "Test Coverage Required:\n" +
                "1. CRUD Operations Testing\n" +
                "2. Authentication & Authorization\n" +
                "3. Data Validation\n" +
                "4. Error Handling\n" +
                "5. Performance Considerations\n\n" +
                "Please provide complete test suite with setup, test cases, and utility functions.";
    }

    private String getUnitTemplate() {
        return "Generate comprehensive unit tests for [Component/Function Name] using [Testing Framework].\n\n" +
                "Component Context:\n" +
                "- Component/Function: [Name and purpose]\n" +
                "- Language: [Programming language]\n" +
                "- Framework: [Testing framework]\n\n" +
                "Test Coverage Requirements:\n" +
                "1. Core Functionality\n" +
                "2. Edge Cases\n" +
                "3. Error Conditions\n" +
                "4. Mock/Stub Requirements\n" +
                "5. State Testing\n\n" +
                "Please generate complete test suite with proper mocking, assertions, and documentation.";
    }

    private String getMobileTemplate() {
        return "Create mobile application test automation for [App Name] using [Framework].\n\n" +
                "Mobile App Context:\n" +
                "- Platform: [iOS/Android/Cross-platform]\n" +
                "- App Type: [Native/Hybrid/React Native]\n" +
                "- Framework: [Appium/Detox/XCUITest/Espresso]\n\n" +
                "Test Scenarios:\n" +
                "1. App Lifecycle\n" +
                "2. UI Interactions\n" +
                "3. Device Features\n" +
                "4. Performance\n" +
                "5. Cross-Device Testing\n\n" +
                "Please provide complete mobile test automation suite with setup instructions.";
    }

    private String getSecurityTemplate() {
        return "Generate security-focused test automation for [Application Name].\n\n" +
                "Security Testing Context:\n" +
                "- Application: [Type and description]\n" +
                "- Framework: [Security testing framework]\n\n" +
                "Security Test Categories:\n" +
                "1. Authentication Testing\n" +
                "2. Authorization Testing\n" +
                "3. Input Validation\n" +
                "4. Data Protection\n" +
                "5. Session Management\n\n" +
                "Please create comprehensive security test suite with attack vectors and validation.";
    }

    private String getPerformanceTemplate() {
        return "Create performance test automation for [Application Name] using [Performance Testing Tool].\n\n" +
                "Performance Testing Context:\n" +
                "- Application: [Description and architecture]\n" +
                "- Tool: [JMeter/Gatling/K6/LoadRunner]\n\n" +
                "Performance Test Types:\n" +
                "1. Load Testing\n" +
                "2. Stress Testing\n" +
                "3. Spike Testing\n" +
                "4. Volume Testing\n" +
                "5. Endurance Testing\n\n" +
                "Please provide complete performance test suite with monitoring and reporting.";
    }
}