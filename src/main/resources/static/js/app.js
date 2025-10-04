// ============================================================================
// FILE: src/main/resources/static/js/app.js
// ============================================================================

const API_BASE_URL = '/api/prompts';

// Template data
const templateData = {
    'web-e2e': {
        title: '🌐 Web Application E2E Testing',
        description: 'Template for end-to-end testing of web applications with user workflows, form validation, and cross-browser compatibility.'
    },
    'api': {
        title: '🔌 API Testing',
        description: 'Comprehensive API testing including CRUD operations, authentication, error handling, and response validation.'
    },
    'unit': {
        title: '🧪 Unit Testing',
        description: 'Unit test generation for functions, methods, and components with mocking and edge case coverage.'
    },
    'mobile': {
        title: '📱 Mobile App Testing',
        description: 'Mobile application testing for iOS/Android including gestures, device features, and responsive design.'
    },
    'security': {
        title: '🔐 Security Testing',
        description: 'Security-focused test automation for authentication, authorization, input validation, and vulnerability testing.'
    },
    'performance': {
        title: '⚡ Performance Testing',
        description: 'Performance test automation for load testing, stress testing, and performance monitoring.'
    }
};

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadTemplates();
    setupEventListeners();
});

function setupEventListeners() {
    // Form submission
    const form = document.getElementById('promptForm');
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            generatePrompt();
        });
    }
}

async function generatePrompt() {
    const form = document.getElementById('promptForm');
    const formData = new FormData(form);

    // Get selected requirements
    const requirements = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    checkboxes.forEach(cb => {
        requirements.push(cb.value);
    });

    // Build request object
    const requestData = {
        appType: formData.get('appType'),
        testType: formData.get('testType'),
        framework: formData.get('framework'),
        featureName: formData.get('featureName'),
        featureDescription: formData.get('featureDescription'),
        userStory: formData.get('userStory'),
        programmingLanguage: formData.get('programmingLanguage'),
        scenarios: formData.get('scenarios'),
        testData: formData.get('testData'),
        environment: formData.get('environment'),
        constraints: formData.get('constraints'),
        additionalNotes: formData.get('additionalNotes'),
        requirements: requirements
    };

    // Validate required fields
    if (!requestData.featureName || !requestData.featureName.trim()) {
        showAlert('Please enter a feature name', 'warning');
        return;
    }

    try {
        showLoading(true);
        const response = await fetch(`${API_BASE_URL}/generate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) {
            throw new Error('Failed to generate prompt');
        }

        const data = await response.json();
        displayGeneratedPrompt(data.generatedPrompt);
        showAlert('Prompt generated successfully!', 'success');

    } catch (error) {
        console.error('Error generating prompt:', error);
        showAlert('Error generating prompt. Please try again.', 'danger');
    } finally {
        showLoading(false);
    }
}

async function useTemplate(templateType) {
    try {
        showLoading(true);
        const response = await fetch(`${API_BASE_URL}/template`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ templateType: templateType })
        });

        if (!response.ok) {
            throw new Error('Failed to load template');
        }

        const data = await response.json();

        // Switch to builder tab
        const builderTab = document.querySelector('[data-bs-target="#builder"]');
        if (builderTab) {
            const tab = new bootstrap.Tab(builderTab);
            tab.show();
        }

        // Display the template
        displayGeneratedPrompt(data.generatedPrompt);
        showAlert('Template loaded successfully!', 'success');

    } catch (error) {
        console.error('Error loading template:', error);
        showAlert('Error loading template. Please try again.', 'danger');
    } finally {
        showLoading(false);
    }
}

function displayGeneratedPrompt(prompt) {
    const outputSection = document.getElementById('output');
    const promptTextarea = document.getElementById('generatedPrompt');

    if (promptTextarea) {
        promptTextarea.value = prompt;
    }

    if (outputSection) {
        outputSection.style.display = 'block';
        // Scroll to output
        outputSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }
}

function copyPrompt() {
    const promptTextarea = document.getElementById('generatedPrompt');

    if (!promptTextarea || !promptTextarea.value.trim()) {
        showAlert('No prompt to copy', 'warning');
        return;
    }

    navigator.clipboard.writeText(promptTextarea.value).then(() => {
        showAlert('Prompt copied to clipboard!', 'success');
    }).catch(() => {
        // Fallback for older browsers
        promptTextarea.select();
        document.execCommand('copy');
        showAlert('Prompt copied to clipboard!', 'success');
    });
}

function downloadPrompt() {
    const promptTextarea = document.getElementById('generatedPrompt');
    const prompt = promptTextarea ? promptTextarea.value : '';

    if (!prompt.trim()) {
        showAlert('No prompt to download', 'warning');
        return;
    }

    const featureName = document.getElementById('featureName') ?
                        document.getElementById('featureName').value : 'prompt';
    const filename = `automation-prompt-${featureName.replace(/\s+/g, '-').toLowerCase()}.txt`;

    const blob = new Blob([prompt], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);

    URL.revokeObjectURL(url);
    showAlert('Prompt downloaded successfully!', 'success');
}

function loadTemplates() {
    const templateGrid = document.getElementById('templateGrid');

    if (!templateGrid) return;

    templateGrid.innerHTML = '';

    Object.entries(templateData).forEach(([key, template]) => {
        const templateCard = createTemplateCard(key, template);
        templateGrid.appendChild(templateCard);
    });
}

function createTemplateCard(templateType, template) {
    const col = document.createElement('div');
    col.className = 'col-md-6 col-lg-4 mb-4';

    col.innerHTML = `
        <div class="card template-card h-100" onclick="useTemplate('${templateType}')">
            <div class="card-body">
                <h5 class="card-title">${template.title}</h5>
                <p class="card-text">${template.description}</p>
                <button type="button" class="btn btn-primary btn-sm">
                    Use Template
                </button>
            </div>
        </div>
    `;

    return col;
}

function showAlert(message, type = 'info') {
    // Remove any existing alerts
    const existingAlerts = document.querySelectorAll('.alert-copy');
    existingAlerts.forEach(alert => alert.remove());

    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show alert-copy`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    document.body.appendChild(alertDiv);

    // Auto-dismiss after 3 seconds
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, 3000);
}

function showLoading(show) {
    const generateBtn = document.querySelector('button[onclick="generatePrompt()"]');
    if (!generateBtn) return;

    if (show) {
        generateBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Generating...';
        generateBtn.disabled = true;
    } else {
        generateBtn.innerHTML = '🚀 Generate Test Automation Prompt';
        generateBtn.disabled = false;
    }
}

// Utility functions for other pages
async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE_URL}/history`);
        const history = await response.json();

        const historyContainer = document.getElementById('historyContainer');
        if (historyContainer) {
            displayHistory(history);
        }
    } catch (error) {
        console.error('Error loading history:', error);
        const historyContainer = document.getElementById('historyContainer');
        if (historyContainer) {
            historyContainer.innerHTML = '<div class="alert alert-danger">Error loading history. Please try again.</div>';
        }
    }
}

async function loadStats() {
    try {
        const response = await fetch(`${API_BASE_URL}/stats`);
        const stats = await response.json();

        const statsContainer = document.getElementById('statsContainer');
        if (statsContainer) {
            displayStats(stats);
        }
    } catch (error) {
        console.error('Error loading stats:', error);
        const statsContainer = document.getElementById('statsContainer');
        if (statsContainer) {
            statsContainer.innerHTML = '<div class="alert alert-danger">Error loading statistics. Please try again.</div>';
        }
    }
}

function displayHistory(history) {
    const container = document.getElementById('historyContainer');

    if (!container) return;

    if (history.length === 0) {
        container.innerHTML = '<div class="alert alert-info">No prompt history found.</div>';
        return;
    }

    container.innerHTML = history.map(item => `
        <div class="card mb-3">
            <div class="card-header">
                <strong>${item.featureName}</strong>
                <small class="text-muted float-end">${new Date(item.createdAt).toLocaleString()}</small>
            </div>
            <div class="card-body">
                <p><strong>Type:</strong> ${item.testType} | <strong>Framework:</strong> ${item.framework}</p>
                <details>
                    <summary>View Generated Prompt</summary>
                    <pre class="mt-2 p-2 bg-light">${escapeHtml(item.generatedPrompt)}</pre>
                </details>
            </div>
        </div>
    `).join('');
}

function displayStats(stats) {
    const container = document.getElementById('statsContainer');

    if (!container) return;

    const avgPerDay = stats.totalPrompts > 0 ? Math.round(stats.totalPrompts / 7) : 0;

    container.innerHTML = `
        <div class="row">
            <div class="col-md-4 mb-3">
                <div class="card text-center">
                    <div class="card-body">
                        <h2 class="text-primary display-4">${stats.totalPrompts}</h2>
                        <p class="text-muted">Total Prompts Generated</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <div class="card text-center">
                    <div class="card-body">
                        <h2 class="text-success display-4">${stats.recentPrompts}</h2>
                        <p class="text-muted">Recent Prompts (${stats.period})</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <div class="card text-center">
                    <div class="card-body">
                        <h2 class="text-info display-4">${avgPerDay}</h2>
                        <p class="text-muted">Average per Day</p>
                    </div>
                </div>
            </div>
        </div>
    `;
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}