package com.automationprompt.dto;

import jakarta.validation.constraints.NotBlank;

public class TemplateRequest {
    @NotBlank
    private String templateType;

    public TemplateRequest() {}

    public TemplateRequest(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateType() { return templateType; }
    public void setTemplateType(String templateType) { this.templateType = templateType; }
}