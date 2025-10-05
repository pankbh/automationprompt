# Structured Prompt: Web Page Element Locator Extraction & Page Object Model Generation

## Objective
Extract all UI element locators from a given web page URL and generate a comprehensive TypeScript Playwright Page Object Model (POM) class with best practices.

---

## Input Parameters

### Required Input
```
Web Page URL: [Insert target URL here]
Example: https://www.citivelocity.com/login/
```

### Optional Parameters
- **Framework**: Playwright (default), Selenium, Cypress
- **Language**: TypeScript (default), JavaScript, Python, Java
- **Browser**: Chrome (default), Firefox, Safari
- **Additional Requirements**: Custom locator preferences, specific elements to focus on

---

## Processing Instructions

### Step 1: Page Analysis
1. **Navigate** to the provided URL
2. **Wait** for complete page load (DOM ready, network idle)
3. **Capture** full page screenshot for reference
4. **Extract** HTML source code (outer and inner HTML)
5. **Identify** all interactive and important elements:
   - Input fields (text, email, password, checkbox, radio, etc.)
   - Buttons (submit, action buttons)
   - Links (navigation, action links)
   - Forms
   - Containers and sections
   - Headers and footers
   - Error/success message containers
   - Images and icons
   - Dropdowns and select elements
   - Text areas
   - Labels

### Step 2: Locator Strategy Selection
Apply the following priority order for each element:

**Priority 1: ID**
```typescript
element.locator('#uniqueId')
```

**Priority 2: Name Attribute**
```typescript
element.locator('[name="elementName"]')
```

**Priority 3: Data Attributes**
```typescript
element.locator('[data-testid="element"]')
```

**Priority 4: CSS Selectors**
```typescript
element.locator('input[type="text"][placeholder="Username"]')
```

**Priority 5: XPath (when necessary)**
```typescript
element.locator('//input[@type="text" and contains(@class, "username")]')
```

**Fallback Strategy**
- Use multiple selectors with fallback options
- Implement `.first()` for multiple matches
- Use case-insensitive matching where appropriate

### Step 3: Element Naming Convention
Follow this standardized naming pattern:

**Format**: `<descriptive_name>_<element_type>`

**Examples**:
- `username_input`
- `login_button`
- `remember_me_checkbox`
- `forgot_password_link`
- `error_message_container`
- `submit_button`
- `email_input`
- `search_textbox`

**Rules**:
- Use lowercase with underscores (snake_case)
- Be descriptive and clear
- Include element type suffix
- Avoid abbreviations unless commonly understood

---

## Output Requirements

### Output Format

#### Part 1: Element Locator Documentation
Generate a structured list of all identified elements:

```markdown
## Element Locators

### Form Elements
1. **username_input**
   - ID: `#username`
   - Name: `input[name="username"]`
   - CSS: `input[type="text"][placeholder*="username"]`
   - Description: Username/email input field

2. **password_input**
   - ID: `#password`
   - Name: `input[name="password"]`
   - CSS: `input[type="password"]`
   - Description: Password input field

[Continue for all elements...]
```

#### Part 2: TypeScript Playwright Page Object Model Class

Generate a complete POM class with:

**Required Components**:
1. **Import statements** (Page, Locator from Playwright)
2. **Class declaration** with meaningful name
3. **Page URL constant**
4. **Readonly locator properties** for all elements
5. **Constructor** with page parameter
6. **Locator initialization** in constructor
7. **Action methods** for common operations
8. **Verification methods** for page state
9. **Utility methods** for helper functions

**Code Structure**:
```typescript
import { Page, Locator } from '@playwright/test';

export class [PageName]Page {
  readonly page: Page;
  readonly url = '[PAGE_URL]';
  
  // Element locators (grouped by section)
  readonly element_name: Locator;
  
  constructor(page: Page) {
    this.page = page;
    // Initialize all locators with fallback strategies
  }
  
  // Action methods
  async performAction(): Promise<void> { }
  
  // Verification methods
  async verifyElement(): Promise<boolean> { }
  
  // Utility methods
  async waitForPageLoad(): Promise<void> { }
}
```

#### Part 3: Usage Examples
Provide sample test code demonstrating POM usage:

```typescript
import { test, expect } from '@playwright/test';
import { LoginPage } from './pages/LoginPage';

test('successful login', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('user@example.com', 'password123');
  // Add assertions
});
```

---

## Quality Standards

### Locator Quality Criteria
- ✅ **Unique**: Each locator should uniquely identify one element
- ✅ **Stable**: Resistant to minor UI changes
- ✅ **Readable**: Clear and self-documenting
- ✅ **Maintainable**: Easy to update when page changes
- ✅ **Fast**: Efficient selector performance

### Code Quality Standards
- ✅ Type-safe (TypeScript strict mode compatible)
- ✅ Follows Playwright best practices
- ✅ Includes JSDoc comments for public methods
- ✅ Implements async/await patterns
- ✅ Handles errors gracefully
- ✅ Uses meaningful variable names
- ✅ Groups related elements logically
- ✅ Includes waiting strategies

---

## Additional Requirements

### Must Include:
1. **Multiple fallback selectors** for each critical element
2. **Logical grouping** of elements (header, form, footer, etc.)
3. **Common action methods**:
   - `navigate()` - Navigate to page
   - `waitForPageLoad()` - Wait for page ready
   - `verifyPageElements()` - Verify critical elements
   - Form submission methods
   - Click methods for buttons/links
4. **Error handling** in methods
5. **Comments** explaining complex selectors

### Optional Enhancements:
- Screenshot capture methods
- Wait strategies for dynamic elements
- Form validation methods
- Element state checking (visible, enabled, etc.)
- Data extraction methods (getText, getValue, etc.)

---

## Example Execution

### Input:
```
URL: https://www.citivelocity.com/login/
Framework: Playwright
Language: TypeScript
```

### Expected Output:
1. Complete element inventory with multiple locator strategies
2. Production-ready TypeScript POM class
3. Usage examples and test samples
4. Documentation of locator strategies used

---

## Validation Checklist

Before delivering output, verify:

- [ ] All visible interactive elements identified
- [ ] Each element has at least 2 locator strategies
- [ ] Naming convention followed consistently
- [ ] POM class compiles without TypeScript errors
- [ ] Methods include proper async/await
- [ ] Comments explain non-obvious logic
- [ ] Code follows language style guide
- [ ] No hardcoded waits (use proper waiting strategies)
- [ ] Element grouping is logical and clear
- [ ] Methods have clear, single responsibilities

---

## Error Handling

If page cannot be accessed or analyzed:
1. Report the specific error encountered
2. Suggest alternative approaches
3. Provide partial results if possible
4. Recommend manual inspection steps

---

## Deliverables Summary

### Primary Deliverables:
1. ✅ Element Locator Documentation (Markdown format)
2. ✅ Complete POM Class (TypeScript file)
3. ✅ Usage Examples (Test file samples)

### Supporting Deliverables:
4. ✅ Page Screenshot (for reference)
5. ✅ Locator Strategy Justification
6. ✅ Maintenance Guidelines

---
