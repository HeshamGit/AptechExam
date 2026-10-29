import os
import re

def update_portal_js():
    js_path = os.path.join("app", "src", "main", "assets", "portal", "portal.js")
    with open(js_path, "r", encoding="utf-8") as f:
        content = f.read()

    # 1. Update portal option card text
    old_portal_opt = '<span style="pointer-events: none; flex: 1;">${escapeHtml(opt.text)}</span>'
    new_portal_opt = '<span class="option-card-text" style="pointer-events: none;">${escapeHtml(opt.text)}</span>'
    if old_portal_opt in content:
        content = content.replace(old_portal_opt, new_portal_opt)
        print("Updated portal option card text in portal.js")
    else:
        print("Warning: old_portal_opt not found in portal.js")

    # 2. Update practice option button and indicators
    old_practice_indicators = '''      if (isRightChoice) {
        btnClass = "is-correct";
        indicator = "<span>✓ Correct</span>";
      } else if (isUserChoice) {
        btnClass = "is-wrong";
        indicator = "<span>✗ Wrong</span>";
      }
    } else if (q.isMultiSelect && AppState.practice.multiPending.includes(oIdx)) {
      btnClass = "is-selected-pending";
      indicator = "<span>☑ Selected</span>";
    }

    return `
      <button class="practice-opt-btn ${btnClass}" ${answeredState ? 'disabled' : ''} onclick="onPracticeOptionClick(${oIdx})">
        <span>${escapeHtml(opt.text)}</span>
        ${indicator}
      </button>
    `;'''

    new_practice_indicators = '''      if (isRightChoice) {
        btnClass = "is-correct";
        indicator = `<span class="practice-opt-indicator correct">✓ Correct</span>`;
      } else if (isUserChoice) {
        btnClass = "is-wrong";
        indicator = `<span class="practice-opt-indicator wrong">✗ Wrong</span>`;
      }
    } else if (q.isMultiSelect && AppState.practice.multiPending.includes(oIdx)) {
      btnClass = "is-selected-pending";
      indicator = `<span class="practice-opt-indicator pending">☑ Selected</span>`;
    }

    return `
      <button class="practice-opt-btn ${btnClass}" ${answeredState ? 'disabled' : ''} onclick="onPracticeOptionClick(${oIdx})">
        <span class="practice-opt-text">${escapeHtml(opt.text)}</span>
        ${indicator}
      </button>
    `;'''

    # Normalize line endings for replacement
    content_normalized = content.replace("\r\n", "\n")
    old_norm = old_practice_indicators.replace("\r\n", "\n")
    new_norm = new_practice_indicators.replace("\r\n", "\n")

    if old_norm in content_normalized:
        content_normalized = content_normalized.replace(old_norm, new_norm)
        print("Updated practice option buttons and indicators in portal.js")
    else:
        print("Warning: old_practice_indicators not found in portal.js")

    # 3. Update feedback banner content
    old_feedback = '''    banner.innerHTML = answeredState.isCorrect
      ? `<div><strong>🎉 Spot on!</strong> That is the correct answer.</div>`
      : `<div><strong>💡 Correct Answer:</strong> ${correctOptTexts}</div>`;'''

    new_feedback = '''    banner.innerHTML = answeredState.isCorrect
      ? `<div class="feedback-banner-content"><strong>🎉 Spot on!</strong> That is the correct answer.</div>`
      : `<div class="feedback-banner-content"><strong>💡 Correct Answer:</strong> ${correctOptTexts}</div>`;'''

    old_fb_norm = old_feedback.replace("\r\n", "\n")
    new_fb_norm = new_feedback.replace("\r\n", "\n")

    if old_fb_norm in content_normalized:
        content_normalized = content_normalized.replace(old_fb_norm, new_fb_norm)
        print("Updated feedback banner content in portal.js")
    else:
        print("Warning: old_feedback not found in portal.js")

    # Restore CRLF line endings
    content_final = content_normalized.replace("\n", "\r\n")
    with open(js_path, "w", encoding="utf-8") as f:
        f.write(content_final)


def update_portal_css():
    css_path = os.path.join("app", "src", "main", "assets", "portal", "portal.css")
    with open(css_path, "r", encoding="utf-8") as f:
        content = f.read()

    content_norm = content.replace("\r\n", "\n")

    # 1. Update body text wrapping
    old_body = '''body {
  font-family: var(--font-sans);
  background-color: var(--bg-app);
  color: var(--text-main);
  line-height: 1.5;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  overflow-x: hidden;
}'''

    new_body = '''body {
  font-family: var(--font-sans);
  background-color: var(--bg-app);
  color: var(--text-main);
  line-height: 1.5;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  overflow-x: hidden;
  overflow-wrap: break-word;
}'''

    if old_body in content_norm:
        content_norm = content_norm.replace(old_body, new_body)
        print("Updated body in portal.css")
    else:
        print("Warning: old_body not found in portal.css")

    # 2. Update .q-prompt-text
    old_q_prompt = '''.q-prompt-text {
  font-size: 1.05rem;
  color: var(--text-main);
  line-height: 1.6;
  font-weight: 600;
  margin-bottom: 1.5rem;
}'''

    new_q_prompt = '''.q-prompt-text {
  font-size: 1.05rem;
  color: var(--text-main);
  line-height: 1.6;
  font-weight: 600;
  margin-bottom: 1.5rem;
  overflow-wrap: anywhere;
  word-break: break-word;
}'''

    if old_q_prompt in content_norm:
        content_norm = content_norm.replace(old_q_prompt, new_q_prompt)
        print("Updated .q-prompt-text in portal.css")
    else:
        print("Warning: old_q_prompt not found in portal.css")

    # 3. Update .option-card-label and .option-input-ctrl and add .option-card-text
    old_opt_card = '''.option-card-label {
  display: flex;
  align-items: center;
  gap: 0.85rem;
  min-height: var(--tap-min);
  padding: 0.75rem 1rem;
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all 0.15s ease;
  user-select: none;
  font-size: 0.95rem;
  color: var(--text-main);
}

.option-card-label:hover {
  background: var(--bg-card-hover);
  border-color: var(--aptech-red);
}

.option-card-label.selected {
  background: var(--aptech-red-light);
  border-color: var(--aptech-red);
  box-shadow: 0 0 0 1px var(--aptech-red);
  font-weight: 600;
}

.option-input-ctrl {
  width: 20px;
  height: 20px;
  accent-color: var(--aptech-red);
  flex-shrink: 0;
  pointer-events: none;
}'''

    new_opt_card = '''.option-card-label {
  display: flex;
  align-items: center;
  gap: 0.85rem;
  min-height: var(--tap-min);
  padding: 0.75rem 1rem;
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all 0.15s ease;
  user-select: none;
  font-size: 0.95rem;
  color: var(--text-main);
  width: 100%;
  box-sizing: border-box;
}

.option-card-label:hover {
  background: var(--bg-card-hover);
  border-color: var(--aptech-red);
}

.option-card-label.selected {
  background: var(--aptech-red-light);
  border-color: var(--aptech-red);
  box-shadow: 0 0 0 1px var(--aptech-red);
  font-weight: 600;
}

.option-input-ctrl {
  width: 20px;
  height: 20px;
  accent-color: var(--aptech-red);
  flex: 0 0 20px;
  flex-shrink: 0;
  pointer-events: none;
}

.option-card-text {
  flex: 1 1 auto;
  min-width: 0;
  overflow-wrap: anywhere;
  word-break: break-word;
  line-height: 1.45;
}'''

    if old_opt_card in content_norm:
        content_norm = content_norm.replace(old_opt_card, new_opt_card)
        print("Updated .option-card-label and added .option-card-text in portal.css")
    else:
        print("Warning: old_opt_card not found in portal.css")

    # 4. Update .practice-container, .practice-card, .practice-card-prompt
    old_practice_box = '''.practice-container {
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 60px);
  max-width: 800px;
  margin: 0 auto;
  width: 100%;
  padding: 1rem;
}'''

    new_practice_box = '''.practice-container {
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 60px);
  max-width: 800px;
  margin: 0 auto;
  width: 100%;
  padding: 1rem;
  box-sizing: border-box;
}'''

    if old_practice_box in content_norm:
        content_norm = content_norm.replace(old_practice_box, new_practice_box)
        print("Updated .practice-container in portal.css")

    old_p_card = '''.practice-card {
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  padding: 1.75rem 1.25rem;
  box-shadow: var(--shadow-sm);
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
}

.practice-card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.85rem;
}

.practice-card-prompt {
  font-size: 1.15rem;
  font-weight: 700;
  color: var(--text-main);
  line-height: 1.55;
  margin-bottom: 1.5rem;
}'''

    new_p_card = '''.practice-card {
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  padding: 1.75rem 1.25rem;
  box-shadow: var(--shadow-sm);
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  width: 100%;
  box-sizing: border-box;
}

.practice-card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.85rem;
}

.practice-card-prompt {
  font-size: 1.15rem;
  font-weight: 700;
  color: var(--text-main);
  line-height: 1.55;
  margin-bottom: 1.5rem;
  overflow-wrap: anywhere;
  word-break: break-word;
}'''

    if old_p_card in content_norm:
        content_norm = content_norm.replace(old_p_card, new_p_card)
        print("Updated .practice-card and .practice-card-prompt in portal.css")
    else:
        print("Warning: old_p_card not found in portal.css")

    # 5. Update .practice-options-list, .practice-opt-btn and add text/indicator/feedback classes
    old_p_opts = '''.practice-options-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.practice-opt-btn {
  min-height: var(--tap-min);
  padding: 0.85rem 1.15rem;
  border-radius: var(--radius-md);
  border: 2px solid var(--border-color);
  background: var(--bg-surface);
  color: var(--text-main);
  font-size: 0.95rem;
  text-align: left;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.75rem;
  transition: all 0.2s cubic-bezier(0.16, 1, 0.3, 1);
  font-weight: 500;
}

.practice-opt-btn:hover:not(:disabled) {
  border-color: var(--color-accent);
  background: var(--bg-card-hover);
}

.practice-opt-btn.is-selected-pending {
  border-color: var(--color-accent);
  background: var(--color-accent-bg);
  font-weight: 700;
}

.practice-opt-btn.is-correct {
  border-color: var(--color-correct);
  background: var(--color-correct-bg);
  color: var(--color-correct-text);
  font-weight: 700;
}

.practice-opt-btn.is-wrong {
  border-color: var(--color-wrong);
  background: var(--color-wrong-bg);
  color: var(--color-wrong-text);
  animation: shake 0.4s ease;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  20%, 60% { transform: translateX(-6px); }
  40%, 80% { transform: translateX(6px); }
}

.feedback-banner {
  display: none;
  padding: 1rem;
  border-radius: var(--radius-md);
  margin-top: 1rem;
  animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-5px); }
  to { opacity: 1; transform: translateY(0); }
}

.feedback-banner.correct {
  display: flex;
  background: var(--color-correct-bg);
  border: 1px solid var(--color-correct);
  color: var(--color-correct-text);
}

.feedback-banner.wrong {
  display: flex;
  background: var(--color-wrong-bg);
  border: 1px solid var(--color-wrong);
  color: var(--color-wrong-text);
}'''

    new_p_opts = '''.practice-options-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1rem;
  width: 100%;
}

.practice-opt-btn {
  min-height: var(--tap-min);
  padding: 0.85rem 1rem;
  border-radius: var(--radius-md);
  border: 2px solid var(--border-color);
  background: var(--bg-surface);
  color: var(--text-main);
  font-size: 0.95rem;
  text-align: left;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.75rem;
  transition: all 0.2s cubic-bezier(0.16, 1, 0.3, 1);
  font-weight: 500;
  width: 100%;
  box-sizing: border-box;
  overflow: hidden;
}

.practice-opt-btn:hover:not(:disabled) {
  border-color: var(--color-accent);
  background: var(--bg-card-hover);
}

.practice-opt-btn:disabled {
  cursor: default;
}

.practice-opt-btn.is-selected-pending {
  border-color: var(--color-accent);
  background: var(--color-accent-bg);
  font-weight: 700;
}

.practice-opt-btn.is-correct {
  border-color: var(--color-correct);
  background: var(--color-correct-bg);
  color: var(--color-correct-text);
  font-weight: 700;
}

.practice-opt-btn.is-wrong {
  border-color: var(--color-wrong);
  background: var(--color-wrong-bg);
  color: var(--color-wrong-text);
  animation: shake 0.4s ease;
}

.practice-opt-text {
  flex: 1 1 auto;
  min-width: 0;
  overflow-wrap: anywhere;
  word-break: break-word;
  line-height: 1.45;
}

.practice-opt-indicator {
  flex: 0 0 auto;
  flex-shrink: 0;
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  font-size: 0.78rem;
  font-weight: 700;
  white-space: nowrap;
  padding: 0.22rem 0.55rem;
  border-radius: var(--radius-sm);
  line-height: 1.2;
}

.practice-opt-indicator.correct {
  background: var(--color-correct);
  color: #FFFFFF;
}

.practice-opt-indicator.wrong {
  background: var(--color-wrong);
  color: #FFFFFF;
}

.practice-opt-indicator.pending {
  background: var(--color-accent);
  color: #FFFFFF;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  20%, 60% { transform: translateX(-6px); }
  40%, 80% { transform: translateX(6px); }
}

.feedback-banner {
  display: none;
  padding: 1rem;
  border-radius: var(--radius-md);
  margin-top: 1rem;
  animation: fadeIn 0.2s ease;
  width: 100%;
  box-sizing: border-box;
  overflow: hidden;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-5px); }
  to { opacity: 1; transform: translateY(0); }
}

.feedback-banner.correct {
  display: flex;
  background: var(--color-correct-bg);
  border: 1px solid var(--color-correct);
  color: var(--color-correct-text);
}

.feedback-banner.wrong {
  display: flex;
  background: var(--color-wrong-bg);
  border: 1px solid var(--color-wrong);
  color: var(--color-wrong-text);
}

.feedback-banner-content {
  flex: 1 1 auto;
  min-width: 0;
  width: 100%;
  overflow-wrap: anywhere;
  word-break: break-word;
  line-height: 1.5;
}'''

    if old_p_opts in content_norm:
        content_norm = content_norm.replace(old_p_opts, new_p_opts)
        print("Updated .practice-options-list and .practice-opt-btn and .feedback-banner in portal.css")
    else:
        print("Warning: old_p_opts not found in portal.css")

    # 6. Update review cards and pills
    old_review = '''.review-item-card {
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  padding: 1.25rem;
}'''

    new_review = '''.review-item-card {
  background: var(--bg-surface);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  padding: 1.25rem;
  overflow-wrap: anywhere;
  word-break: break-word;
  box-sizing: border-box;
}'''

    if old_review in content_norm:
        content_norm = content_norm.replace(old_review, new_review)
        print("Updated .review-item-card in portal.css")

    old_pill = '''.review-opt-pill {
  padding: 0.4rem 0.75rem;
  border-radius: var(--radius-sm);
  font-size: 0.85rem;
  margin-top: 0.35rem;
}'''

    new_pill = '''.review-opt-pill {
  padding: 0.4rem 0.75rem;
  border-radius: var(--radius-sm);
  font-size: 0.85rem;
  margin-top: 0.35rem;
  overflow-wrap: anywhere;
  word-break: break-word;
  box-sizing: border-box;
}'''

    if old_pill in content_norm:
        content_norm = content_norm.replace(old_pill, new_pill)
        print("Updated .review-opt-pill in portal.css")

    # 7. Add responsive tweaks for mobile screens
    if "@media (max-width: 480px)" not in content_norm:
        mobile_media = '''
@media (max-width: 480px) {
  .practice-container {
    padding: 0.75rem;
  }
  .practice-card {
    padding: 1.25rem 0.85rem;
  }
  .practice-opt-btn {
    padding: 0.75rem 0.85rem;
    gap: 0.5rem;
  }
  .practice-card-prompt {
    font-size: 1.05rem;
  }
}
'''
        content_norm += mobile_media
        print("Added @media (max-width: 480px) in portal.css")

    content_final = content_norm.replace("\n", "\r\n")
    with open(css_path, "w", encoding="utf-8") as f:
        f.write(content_final)

if __name__ == "__main__":
    update_portal_js()
    update_portal_css()
    print("Done applying responsive fixes!")
