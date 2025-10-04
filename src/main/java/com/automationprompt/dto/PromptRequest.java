package com.automationprompt.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class PromptRequest {
    @NotBlank
    private String appType;

    @NotBlank
    private String testType;

    @NotBlank
    private String framework;

    @NotBlank
    private String featureName;

    private String featureDescription;
    private String userStory;

    @NotBlank
    private String programmingLanguage;

    private String scenarios;
    private String testData;
    private String environment;
    private String constraints;
    private String additionalNotes;
    private List<String> requirements;

    public PromptRequest() {}

    public PromptRequest(String appType, String testType, String framework, String featureName,
                         String programmingLanguage) {
        this.appType = appType;
        this.testType = testType;
        this.framework = framework;
        this.featureName = featureName;
        this.programmingLanguage = programmingLanguage;
    }

    public String getAppType() { return appType; }
    public void setAppType(String appType) { this.appType = appType; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public String getFramework() { return framework; }
    public void setFramework(String framework) { this.framework = framework; }

    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }

    public String getFeatureDescription() { return featureDescription; }
    public void setFeatureDescription(String featureDescription) { this.featureDescription = featureDescription; }

    public String getUserStory() { return userStory; }
    public void setUserStory(String userStory) { this.userStory = userStory; }

    public String getProgrammingLanguage() { return programmingLanguage; }
    public void setProgrammingLanguage(String programmingLanguage) { this.programmingLanguage = programmingLanguage; }

    public String getScenarios() { return scenarios; }
    public void setScenarios(String scenarios) { this.scenarios = scenarios; }

    public String getTestData() { return testData; }
    public void setTestData(String testData) { this.testData = testData; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public String getConstraints() { return constraints; }
    public void setConstraints(String constraints) { this.constraints = constraints; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }

    public String getPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("App Type: ").append(appType).append("\n");
        sb.append("Test Type: ").append(testType).append("\n");
        sb.append("Framework: ").append(framework).append("\n");
        sb.append("Feature Name: ").append(featureName).append("\n");
        if (featureDescription != null && !featureDescription.isEmpty())
            sb.append("Feature Description: ").append(featureDescription).append("\n");
        if (userStory != null && !userStory.isEmpty())
            sb.append("User Story: ").append(userStory).append("\n");
        sb.append("Programming Language: ").append(programmingLanguage).append("\n");
        if (scenarios != null && !scenarios.isEmpty())
            sb.append("Scenarios: ").append(scenarios).append("\n");
        if (testData != null && !testData.isEmpty())
            sb.append("Test Data: ").append(testData).append("\n");
        if (environment != null && !environment.isEmpty())
            sb.append("Environment: ").append(environment).append("\n");
        if (constraints != null && !constraints.isEmpty())
            sb.append("Constraints: ").append(constraints).append("\n");
        if (additionalNotes != null && !additionalNotes.isEmpty())
            sb.append("Additional Notes: ").append(additionalNotes).append("\n");
        if (requirements != null && !requirements.isEmpty())
            sb.append("Requirements: ").append(String.join(", ", requirements)).append("\n");
        return sb.toString();
    }
}
