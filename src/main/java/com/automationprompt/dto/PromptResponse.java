package com.automationprompt.dto;

public class PromptResponse {
    private String generatedPrompt;
    private String templateType;
    private long timestamp;

    public PromptResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public PromptResponse(String generatedPrompt, String templateType) {
        this.generatedPrompt = generatedPrompt;
        this.templateType = templateType;
        this.timestamp = System.currentTimeMillis();
    }

    public String getGeneratedPrompt() { return generatedPrompt; }
    public void setGeneratedPrompt(String generatedPrompt) { this.generatedPrompt = generatedPrompt; }

    public String getTemplateType() { return templateType; }
    public void setTemplateType(String templateType) { this.templateType = templateType; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
