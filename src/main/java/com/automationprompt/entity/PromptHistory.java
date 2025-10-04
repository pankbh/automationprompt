package com.automationprompt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prompt_history")
public class PromptHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_type", nullable = false)
    private String appType;

    @Column(name = "test_type", nullable = false)
    private String testType;

    @Column(name = "framework", nullable = false)
    private String framework;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "programming_language", nullable = false)
    private String programmingLanguage;

    @Column(name = "generated_prompt", columnDefinition = "TEXT")
    private String generatedPrompt;

    @Column(name = "template_type")
    private String templateType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public PromptHistory() {}

    public PromptHistory(String appType, String testType, String framework,
                         String featureName, String programmingLanguage,
                         String generatedPrompt, String templateType) {
        this.appType = appType;
        this.testType = testType;
        this.framework = framework;
        this.featureName = featureName;
        this.programmingLanguage = programmingLanguage;
        this.generatedPrompt = generatedPrompt;
        this.templateType = templateType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAppType() { return appType; }
    public void setAppType(String appType) { this.appType = appType; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public String getFramework() { return framework; }
    public void setFramework(String framework) { this.framework = framework; }

    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }

    public String getProgrammingLanguage() { return programmingLanguage; }
    public void setProgrammingLanguage(String programmingLanguage) { this.programmingLanguage = programmingLanguage; }

    public String getGeneratedPrompt() { return generatedPrompt; }
    public void setGeneratedPrompt(String generatedPrompt) { this.generatedPrompt = generatedPrompt; }

    public String getTemplateType() { return templateType; }
    public void setTemplateType(String templateType) { this.templateType = templateType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
