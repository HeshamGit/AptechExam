/**
 * Aptech Interactive Exam Portal Engine
 * Supports Mode 1: Authentic Timed Aptech Exam Portal Simulation
 * Supports Mode 2: 1-Question-at-a-Time Instant Evaluation Practice
 */

// Full offline manifest of all subjects across 4 semesters
const SUBJECTS_MANIFEST = [
  // Semester 1
  { key: "html5", title: "Building Next Generation WebSites (HTML5)", sem: 1, samples: 14 },
  { key: "elementary_c", title: "Logic Building & Programming with C", sem: 1, samples: 9 },
  { key: "programming_principles", title: "Programming Principles & Techniques", sem: 1, samples: 5 },
  { key: "xml", title: "Markup Language & JSON (XML Simplified)", sem: 1, samples: 7 },
  { key: "oop_concept", title: "Object Oriented Programming Concepts", sem: 1, samples: 3 },
  { key: "sql_server", title: "SQL Server 2012 Database", sem: 1, samples: 11 },
  { key: "ui_ux", title: "UI & UX for Responsive Design", sem: 1, samples: 9 },
  { key: "google_apps", title: "Web Productivity Tools (Google Apps)", sem: 1, samples: 4 },

  // Semester 2
  { key: "java", title: "Fundamentals of Java SE 7", sem: 2, samples: 5 },
  { key: "cSharp", title: "Application Programming in C#", sem: 2, samples: 5 },
  { key: "red_hat", title: "Fundamentals of Linux Operating System", sem: 2, samples: 11 },
  { key: "iot", title: "Fundamentals of Internet of Things (IoT)", sem: 2, samples: 1 },
  { key: "seo", title: "Optimize Web for Search Engines (SEO)", sem: 2, samples: 2 },
  { key: "oop_in_java", title: "Object-Oriented Programming with Java", sem: 2, samples: 6 },
  { key: "online_ad", title: "Online Advertising & Management", sem: 2, samples: 2 },

  // Semester 3
  { key: "agile", title: "Agile SDLC for Software Projects", sem: 3, samples: 2 },
  { key: "dev_app_java", title: "Developing Apps with Java Web Frameworks", sem: 3, samples: 3 },
  { key: "fund_java_enterprise", title: "Intro to Web & Enterprise Applications", sem: 3, samples: 2 },
  { key: "web_component", title: "Web Component Development with Java", sem: 3, samples: 3 },

  // Semester 4
  { key: "ms_project", title: "Software Project Management", sem: 4, samples: 9 },
  { key: "soft_eng", title: "Software Engineering Principles", sem: 4, samples: 10 },
  { key: "ejb", title: "Enterprise Application Development (EJB)", sem: 4, samples: 2 },
  { key: "cloud_platform", title: "Working with Cloud Platform Services", sem: 4, samples: 5 },
  { key: "java_web_services", title: "Creating Services for Web (JAX-WS)", sem: 4, samples: 4 },
  { key: "testing_android", title: "Testing Android Applications", sem: 4, samples: 3 },
  { key: "android_app", title: "Developing Mobile Apps using Android", sem: 4, samples: 7 },
  { key: "ajax_java", title: "Rich Internet Applications (AJAX Java)", sem: 4, samples: 3 },
  { key: "mysql", title: "Querying with MySQL Database", sem: 4, samples: 13 },
  { key: "apache", title: "Working with Open Source Web Server", sem: 4, samples: 12 },
  { key: "php", title: "Web Application Development using PHP", sem: 4, samples: 4 }
];

// Utility: HTML Entity Escaping to prevent HTML tags from rendering blank
function escapeHtml(str) {
  if (str == null) return "";
  return String(str)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

// App State
const AppState = {
  currentView: "catalog", // 'catalog', 'portal', 'scorecard', 'practice', 'practice_summary'
  theme: "light",
  currentSubject: null,
  currentSample: null,
  examMeta: {
    code: "EXAM",
    date: "",
    title: "",
    totalMarks: 20,
    noOfQuestions: 0
  },
  questions: [],

  // Mode 1: Portal State
  portal: {
    currentIndex: 0,
    answers: {},       // qIndex -> [optIdx, ...]
    flagged: new Set(),
    visited: new Set(),
    timerSeconds: 2700, // 45 minutes
    timerInterval: null,
    isSubmitted: false,
    startTime: null,
    timeTakenSeconds: 0
  },

  // Mode 2: Practice State
  practice: {
    currentIndex: 0,
    questions: [],
    streak: 0,
    bestStreak: 0,
    answers: {},       // qIndex -> { selected: idx, isCorrect: bool }
    multiPending: [],  // for multi-select questions
    autoAdvance: false,
    autoAdvanceTimeout: null,
    isMistakesOnly: false
  }
};

// ========================================================
// INITIALIZATION
// ========================================================
document.addEventListener("DOMContentLoaded", () => {
  initTheme();
  parseUrlParams();
  renderCatalog();
});

function initTheme() {
  const savedTheme = localStorage.getItem("aptech_theme") || "light";
  setTheme(savedTheme);
}

function toggleTheme() {
  const next = AppState.theme === "dark" ? "light" : "dark";
  setTheme(next);
}

function setTheme(theme) {
  AppState.theme = theme;
  document.documentElement.setAttribute("data-theme", theme);
  localStorage.setItem("aptech_theme", theme);
  const icon = document.getElementById("themeIcon");
  if (icon) {
    icon.textContent = theme === "dark" ? "☀️" : "🌙";
  }
}

function parseUrlParams() {
  const params = new URLSearchParams(window.location.search);
  const subject = params.get("subject");
  const sample = params.get("sample");
  const mode = params.get("mode");

  if (subject && sample) {
    startExam(subject, sample, mode || "portal");
  } else {
    showView("catalog");
  }
}

function showView(viewName) {
  AppState.currentView = viewName;
  document.querySelectorAll(".view-container").forEach(el => el.classList.remove("active"));
  const target = document.getElementById("view-" + viewName);
  if (target) {
    target.classList.add("active");
  }
  window.scrollTo(0, 0);
}

// Native Bridge Helpers
function triggerHaptic(duration = 20) {
  try {
    if (window.AndroidBridge && typeof window.AndroidBridge.vibrate === "function") {
      window.AndroidBridge.vibrate(duration);
    } else if (navigator.vibrate) {
      navigator.vibrate(duration);
    }
  } catch (e) {
    // ignore
  }
}

function showToast(msg) {
  if (window.AndroidBridge && typeof window.AndroidBridge.showToast === "function") {
    window.AndroidBridge.showToast(msg);
  } else {
    let toast = document.getElementById("webToastNotification");
    if (!toast) {
      toast = document.createElement("div");
      toast.id = "webToastNotification";
      toast.className = "web-toast";
      document.body.appendChild(toast);
    }
    toast.textContent = msg;
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), 2600);
  }
}

// ========================================================
// HTML EXAM PARSER (Enhanced for 100% Exam Compatibility)
// ========================================================
async function loadExamData(subject, sample) {
  let htmlText = "";
  const relativePath = subject + "/" + sample + ".htm";

  // 1. Try Android Native Bridge Asset Loader
  if (window.AndroidBridge && typeof window.AndroidBridge.loadAsset === "function") {
    try {
      htmlText = window.AndroidBridge.loadAsset(relativePath);
    } catch (e) {
      console.warn("Native bridge loadAsset failed, falling back to fetch", e);
    }
  }

  // 2. Fallback to standard fetch across web environments (root, portal subfolder, and absolute)
  if (!htmlText) {
    const candidateUrls = [
      relativePath,
      "./" + relativePath,
      "../" + relativePath
    ];
    let lastError = null;
    for (const url of candidateUrls) {
      try {
        const response = await fetch(url);
        if (response.ok) {
          htmlText = await response.text();
          break;
        }
      } catch (e) {
        lastError = e;
      }
    }
    if (!htmlText) {
      throw new Error(`Failed to load ${relativePath}${lastError ? ": " + lastError.message : ""}`);
    }
  }

  return parseExamHtml(htmlText, subject, sample);
}

function parseExamHtml(html, subject, sample) {
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, "text/html");

  const meta = {
    code: "APTECH-EXAM",
    date: "",
    title: "",
    totalMarks: 20,
    noOfQuestions: 0
  };

  const allTds = doc.querySelectorAll("td");
  let examCodeMatch = null;
  let dateMatch = null;
  let marksMatch = null;

  for (const td of allTds) {
    const txt = (td.textContent || "").trim();
    if (!examCodeMatch && txt.includes("Exam Code:")) {
      examCodeMatch = txt.match(/Exam Code:\s*([A-Za-z0-9_-]+)/i);
    }
    if (!dateMatch && txt.includes("Date:")) {
      dateMatch = txt.match(/Date:\s*([0-9\/.-]+)/i);
    }
    if (!marksMatch && txt.includes("Total Marks:")) {
      marksMatch = txt.match(/Total Marks:\s*([0-9.]+)/i);
    }
  }

  if (examCodeMatch) meta.code = examCodeMatch[1];
  if (dateMatch) meta.date = dateMatch[1];
  if (marksMatch) meta.totalMarks = parseFloat(marksMatch[1]);

  // Find Subject Title from manifest
  const subjObj = SUBJECTS_MANIFEST.find(s => s.key === subject);
  meta.title = subjObj ? subjObj.title : subject;

  const questions = [];
  let currentModule = meta.title;
  let currentQ = null;

  for (const td of allTds) {
    const txt = (td.textContent || "").trim();

    // Check for Module header row
    if ((txt.includes("No Of Questions:") || txt.includes("Total Marks:")) && !txt.includes("Aptech Computer Education")) {
      const modMatch = txt.match(/^(.*?)\s*No Of Questions:/);
      if (modMatch && modMatch[1].trim()) {
        currentModule = modMatch[1].trim();
      }
    }

    // Check for Question prompt row - robust match across all 173 exams
    const qMatch = txt.match(/^(\d+)\.\s+(.+)/s);
    const isHeaderRow = txt.includes("Aptech Computer Education") || txt.includes("Exam Code:") || txt.includes("Total Marks:");

    if (qMatch && !isHeaderRow) {
      const qNum = parseInt(qMatch[1]);
      const rawPrompt = qMatch[2];

      const markMatch = rawPrompt.match(/\(Mark is ([\d\.]+) - Difficulty\s*is (\d+)\)/i);
      const mark = markMatch ? parseFloat(markMatch[1]) : 1.0;
      const difficulty = markMatch ? parseInt(markMatch[2]) : 2;

      let cleanPromptHtml = td.innerHTML
        .replace(/^\s*\d+\.\s*/, "")
        .replace(/\(Mark is [\d\.]+ - Difficulty\s*is \d+\)/i, "")
        .replace(/(<br\s*\/?>\s*)+$/gi, "")
        .trim();

      currentQ = {
        index: questions.length,
        num: qNum,
        module: currentModule,
        promptHtml: cleanPromptHtml,
        promptText: rawPrompt.replace(/\(Mark is [\d\.]+ - Difficulty\s*is \d+\)/i, "").trim(),
        mark: mark,
        difficulty: difficulty,
        options: [],
        correct: [],
        isMultiSelect: false
      };
      questions.push(currentQ);
    } else if (currentQ && td.querySelector("ul")) {
      // Option items
      const lis = Array.from(td.querySelectorAll("li"));
      for (const li of lis) {
        const optHtml = li.innerHTML.trim();
        const optText = (li.textContent || "").trim();
        if (!optText) continue;

        let isCorrect = false;
        const styleAttr = (li.getAttribute("style") || "").toLowerCase();
        const parentStyle = li.parentElement ? (li.parentElement.getAttribute("style") || "").toLowerCase() : "";
        const combinedStyle = styleAttr + " " + parentStyle;

        if (
          li.classList.contains("answer-style") ||
          combinedStyle.includes("color:red") ||
          combinedStyle.includes("#ff0000") ||
          combinedStyle.includes("#50c878")
        ) {
          isCorrect = true;
        }

        const optIdx = currentQ.options.length;
        currentQ.options.push({
          index: optIdx,
          text: optText,
          html: optHtml
        });

        if (isCorrect) {
          currentQ.correct.push(optIdx);
        }
      }

      const lowerPrompt = currentQ.promptText.toLowerCase();
      if (
        currentQ.correct.length > 1 ||
        /(choose|select)\s+(two|three|four|\d+)/i.test(lowerPrompt) ||
        lowerPrompt.includes("choose all") ||
        lowerPrompt.includes("select all") ||
        lowerPrompt.includes("multiple")
      ) {
        currentQ.isMultiSelect = true;
      }
    }
  }

  meta.noOfQuestions = questions.length;
  return { meta, questions };
}

// ========================================================
// CATALOG CONTROLLER
// ========================================================
let activeSemFilter = "all";

function renderCatalog() {
  const container = document.getElementById("subjectsGrid");
  if (!container) return;

  const searchQuery = (document.getElementById("catalogSearchInput")?.value || "").toLowerCase().trim();

  const filtered = SUBJECTS_MANIFEST.filter(item => {
    const matchesSem = activeSemFilter === "all" || item.sem === parseInt(activeSemFilter);
    const matchesQuery = !searchQuery || item.title.toLowerCase().includes(searchQuery) || item.key.toLowerCase().includes(searchQuery);
    return matchesSem && matchesQuery;
  });

  container.innerHTML = filtered.map(subj => `
    <div class="subject-card" onclick="openSubjectPicker('${subj.key}')">
      <div>
        <div class="subject-card-top">
          <span class="sem-tag">Semester ${subj.sem}</span>
          <span class="sample-count-badge">${subj.samples} Exams</span>
        </div>
        <h3 class="subject-title">${subj.title}</h3>
      </div>
      <div class="subject-actions-hint">
        <span>⚡ Take Practice or Timed Exam →</span>
      </div>
    </div>
  `).join("");
}

function filterBySem(sem, btn) {
  activeSemFilter = sem;
  document.querySelectorAll(".sem-chip").forEach(el => el.classList.remove("active"));
  if (btn) btn.classList.add("active");
  renderCatalog();
}

function onSearchInput() {
  renderCatalog();
}

// Modal / Bottom Sheet Picker
let selectedPickerSubject = null;
let selectedPickerSample = "sample1";

function openSubjectPicker(subjectKey) {
  const subj = SUBJECTS_MANIFEST.find(s => s.key === subjectKey);
  if (!subj) return;

  selectedPickerSubject = subj;
  selectedPickerSample = "sample1";

  document.getElementById("modalSubjTitle").textContent = subj.title;

  const samplePills = document.getElementById("samplePillsContainer");
  samplePills.innerHTML = Array.from({ length: subj.samples }, (_, i) => {
    const sId = "sample" + (i + 1);
    const isSelected = i === 0 ? "selected" : "";
    return `<button class="sample-pill-btn ${isSelected}" onclick="onSelectSamplePill('${sId}', this)">Sample ${i + 1}</button>`;
  }).join("");

  document.getElementById("examPickerModal").classList.add("open");
}

function onSelectSamplePill(sampleId, btn) {
  selectedPickerSample = sampleId;
  document.querySelectorAll(".sample-pill-btn").forEach(el => el.classList.remove("selected"));
  btn.classList.add("selected");
}

function closePickerModal() {
  document.getElementById("examPickerModal").classList.remove("open");
}

function startChosenExam(mode) {
  closePickerModal();
  if (mode === "classic") {
    const relativePath = selectedPickerSubject.key + "/" + selectedPickerSample + ".htm";
    if (window.AndroidBridge && typeof window.AndroidBridge.openClassicExam === "function") {
      window.AndroidBridge.openClassicExam(selectedPickerSubject.key, selectedPickerSample);
    } else {
      const isSubfolder = window.location.pathname.endsWith("/portal/") || 
                          window.location.pathname.endsWith("/portal/index.html") ||
                          window.location.pathname.includes("/portal/");
      const targetUrl = isSubfolder ? "../" + relativePath : "./" + relativePath;
      window.open(targetUrl, "_blank");
    }
    return;
  }
  startExam(selectedPickerSubject.key, selectedPickerSample, mode);
}

// ========================================================
// START EXAM (ROUTER)
// ========================================================
async function startExam(subjectKey, sampleKey, mode) {
  try {
    const data = await loadExamData(subjectKey, sampleKey);
    AppState.currentSubject = subjectKey;
    AppState.currentSample = sampleKey;
    AppState.examMeta = data.meta;
    AppState.questions = data.questions;

    if (AppState.questions.length === 0) {
      alert("No questions found in this exam file.");
      showView("catalog");
      return;
    }

    if (mode === "portal") {
      initPortalMode();
    } else {
      initPracticeMode();
    }
  } catch (err) {
    console.error("Failed to start exam:", err);
    alert("Could not load exam: " + err.message);
    showView("catalog");
  }
}

// ========================================================
// MODE 1: APTECH EXAM PORTAL CONTROLLER
// ========================================================
function initPortalMode() {
  AppState.portal.currentIndex = 0;
  AppState.portal.answers = {};
  AppState.portal.flagged = new Set();
  AppState.portal.visited = new Set([0]);
  AppState.portal.timerSeconds = 2700; // 45 mins
  AppState.portal.isSubmitted = false;
  AppState.portal.startTime = Date.now();

  // Populate Header Bar
  document.getElementById("portalExamCode").textContent = AppState.examMeta.code;
  document.getElementById("portalExamDate").textContent = AppState.examMeta.date || "Active Session";
  document.getElementById("portalSubjHeader").textContent = AppState.examMeta.title;

  startPortalTimer();
  renderPortalQuestion();
  renderPalette();
  showView("portal");
}

function startPortalTimer() {
  if (AppState.portal.timerInterval) {
    clearInterval(AppState.portal.timerInterval);
  }

  const timerEl = document.getElementById("portalTimerBox");

  AppState.portal.timerInterval = setInterval(() => {
    AppState.portal.timerSeconds--;

    if (AppState.portal.timerSeconds <= 0) {
      clearInterval(AppState.portal.timerInterval);
      alert("Time is up! Your exam will now be submitted automatically.");
      onConfirmSubmitExam();
      return;
    }

    const mins = Math.floor(AppState.portal.timerSeconds / 60);
    const secs = AppState.portal.timerSeconds % 60;
    const formatted = `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    timerEl.textContent = formatted;

    if (AppState.portal.timerSeconds < 300) {
      timerEl.classList.add("timer-urgent");
    } else {
      timerEl.classList.remove("timer-urgent");
    }
  }, 1000);
}

function renderPortalQuestion() {
  const idx = AppState.portal.currentIndex;
  const q = AppState.questions[idx];
  if (!q) return;

  AppState.portal.visited.add(idx);

  // Update Question Labels
  document.getElementById("portalQNumber").textContent = `Question ${idx + 1} of ${AppState.questions.length}`;
  document.getElementById("portalQMarks").textContent = `Mark: ${q.mark} | Diff: ${q.difficulty}`;
  document.getElementById("portalQPrompt").innerHTML = q.promptHtml;

  // Options with HTML escaping to prevent blank tags like <aside>
  const container = document.getElementById("portalOptionsContainer");
  const selectedIndices = AppState.portal.answers[idx] || [];

  container.innerHTML = q.options.map((opt, oIdx) => {
    const isChecked = selectedIndices.includes(oIdx);
    const inputType = q.isMultiSelect ? "checkbox" : "radio";
    return `
      <div class="option-card-label ${isChecked ? 'selected' : ''}" 
           role="${inputType}" 
           aria-checked="${isChecked}" 
           onclick="onPortalOptionSelect(${oIdx}, event)">
        <input type="${inputType}" name="portal_q_option" class="option-input-ctrl" ${isChecked ? 'checked' : ''} tabindex="-1" style="pointer-events: none;" />
        <span class="option-card-text" style="pointer-events: none;">${escapeHtml(opt.text)}</span>
      </div>
    `;
  }).join("");

  // Navigation Buttons
  document.getElementById("btnPortalPrev").disabled = (idx === 0);
  const nextBtn = document.getElementById("btnPortalNext");
  nextBtn.textContent = (idx === AppState.questions.length - 1) ? "Submit Exam" : "Next →";

  // Flag state
  const isFlagged = AppState.portal.flagged.has(idx);
  const flagBtn = document.getElementById("btnPortalFlag");
  if (isFlagged) {
    flagBtn.classList.add("flagged");
    flagBtn.innerHTML = "★ Marked for Review";
  } else {
    flagBtn.classList.remove("flagged");
    flagBtn.innerHTML = "☆ Mark for Review";
  }

  updatePortalStatusCounts();
  renderPalette();
}

function onPortalOptionSelect(optIdx, event) {
  if (event) {
    if (typeof event.preventDefault === "function") event.preventDefault();
    if (typeof event.stopPropagation === "function") event.stopPropagation();
  }
  triggerHaptic(15);
  const idx = AppState.portal.currentIndex;
  const q = AppState.questions[idx];

  if (q.isMultiSelect) {
    const current = AppState.portal.answers[idx] || [];
    if (current.includes(optIdx)) {
      const updated = current.filter(i => i !== optIdx);
      if (updated.length > 0) {
        AppState.portal.answers[idx] = updated;
      } else {
        delete AppState.portal.answers[idx];
      }
    } else {
      AppState.portal.answers[idx] = [...current, optIdx];
    }
  } else {
    AppState.portal.answers[idx] = [optIdx];
  }

  renderPortalQuestion();
}

function onPortalPrev() {
  triggerHaptic(10);
  if (AppState.portal.currentIndex > 0) {
    AppState.portal.currentIndex--;
    renderPortalQuestion();
  }
}

function onPortalNext() {
  triggerHaptic(10);
  if (AppState.portal.currentIndex < AppState.questions.length - 1) {
    AppState.portal.currentIndex++;
    renderPortalQuestion();
  } else {
    onSubmitExamClick();
  }
}

function onPortalClear() {
  triggerHaptic(10);
  delete AppState.portal.answers[AppState.portal.currentIndex];
  renderPortalQuestion();
}

function onPortalToggleFlag() {
  triggerHaptic(15);
  const idx = AppState.portal.currentIndex;
  if (AppState.portal.flagged.has(idx)) {
    AppState.portal.flagged.delete(idx);
  } else {
    AppState.portal.flagged.add(idx);
  }
  renderPortalQuestion();
}

function updatePortalStatusCounts() {
  const answeredCount = Object.keys(AppState.portal.answers).filter(k => AppState.portal.answers[k].length > 0).length;
  const flaggedCount = AppState.portal.flagged.size;
  const total = AppState.questions.length;
  const remaining = total - answeredCount;

  document.getElementById("portalCountAnswered").textContent = answeredCount;
  document.getElementById("portalCountFlagged").textContent = flaggedCount;
  document.getElementById("portalCountRemaining").textContent = remaining;
}

// Palette Drawer
function togglePaletteDrawer() {
  triggerHaptic(10);
  document.getElementById("paletteDrawer").classList.toggle("open");
}

function renderPalette() {
  const grid = document.getElementById("paletteGrid");
  if (!grid) return;

  const currentIdx = AppState.portal.currentIndex;

  grid.innerHTML = AppState.questions.map((q, i) => {
    const isAnswered = AppState.portal.answers[i] && AppState.portal.answers[i].length > 0;
    const isFlagged = AppState.portal.flagged.has(i);
    const isVisited = AppState.portal.visited.has(i);
    const isCurrent = (i === currentIdx);

    let statusClass = "status-unvisited";
    if (isAnswered) statusClass = "status-answered";
    else if (isFlagged) statusClass = "status-flagged";
    else if (isVisited) statusClass = "status-unanswered";

    return `
      <button class="palette-num-btn ${statusClass} ${isCurrent ? 'status-current' : ''}" onclick="jumpToQuestion(${i})">
        ${i + 1}
      </button>
    `;
  }).join("");
}

function jumpToQuestion(idx) {
  triggerHaptic(10);
  AppState.portal.currentIndex = idx;
  renderPortalQuestion();
  document.getElementById("paletteDrawer").classList.remove("open");
}

// Submit Examination Flow
function onSubmitExamClick() {
  triggerHaptic(20);
  const total = AppState.questions.length;
  const answered = Object.keys(AppState.portal.answers).filter(k => AppState.portal.answers[k].length > 0).length;
  const flagged = AppState.portal.flagged.size;
  const unanswered = total - answered;

  document.getElementById("submitModalTotal").textContent = total;
  document.getElementById("submitModalAnswered").textContent = answered;
  document.getElementById("submitModalUnanswered").textContent = unanswered;
  document.getElementById("submitModalFlagged").textContent = flagged;

  document.getElementById("submitConfirmModal").classList.add("open");
}

function closeSubmitModal() {
  document.getElementById("submitConfirmModal").classList.remove("open");
}

function onConfirmSubmitExam() {
  closeSubmitModal();
  clearInterval(AppState.portal.timerInterval);
  AppState.portal.timeTakenSeconds = Math.floor((Date.now() - AppState.portal.startTime) / 1000);
  AppState.portal.isSubmitted = true;
  calculateAndRenderScorecard();
}

function calculateAndRenderScorecard() {
  let score = 0;
  let totalPossible = 0;
  let correctCount = 0;
  let incorrectCount = 0;
  let unansweredCount = 0;

  AppState.questions.forEach((q, i) => {
    totalPossible += q.mark;
    const userAnswers = AppState.portal.answers[i] || [];
    if (userAnswers.length === 0) {
      unansweredCount++;
      return;
    }

    // Verify answers
    const correctAnswers = q.correct;
    const isExactMatch = (userAnswers.length === correctAnswers.length) &&
      userAnswers.every(ans => correctAnswers.includes(ans));

    if (isExactMatch) {
      score += q.mark;
      correctCount++;
    } else {
      incorrectCount++;
    }
  });

  const percent = totalPossible > 0 ? Math.round((score / totalPossible) * 100) : 0;
  const passed = percent >= 50;

  // Render Scorecard
  const circle = document.getElementById("scoreCircle");
  if (passed) {
    circle.classList.remove("failed");
    document.getElementById("verdictTitle").textContent = "EXAMINATION PASSED 🏆";
    document.getElementById("verdictSubtitle").textContent = "Congratulations! You have successfully passed the module examination.";
  } else {
    circle.classList.add("failed");
    document.getElementById("verdictTitle").textContent = "NEEDS REVISION 📝";
    document.getElementById("verdictSubtitle").textContent = "You scored below 50%. Review the answers below to strengthen your understanding.";
  }

  document.getElementById("scorePercentNumber").textContent = percent + "%";
  document.getElementById("metricScore").textContent = `${score.toFixed(1)} / ${totalPossible.toFixed(1)}`;
  document.getElementById("metricCorrect").textContent = correctCount;
  document.getElementById("metricIncorrect").textContent = incorrectCount;

  const mins = Math.floor(AppState.portal.timeTakenSeconds / 60);
  const secs = AppState.portal.timeTakenSeconds % 60;
  document.getElementById("metricTime").textContent = `${mins}m ${secs}s`;

  // Render Question by Question Review List with HTML escaping
  renderScorecardReviewList();
  showView("scorecard");
}

function renderScorecardReviewList() {
  const container = document.getElementById("scorecardReviewList");
  if (!container) return;

  container.innerHTML = AppState.questions.map((q, i) => {
    const userAns = AppState.portal.answers[i] || [];
    const correctAns = q.correct;

    const isAnswered = userAns.length > 0;
    const isCorrect = isAnswered && (userAns.length === correctAns.length) && userAns.every(a => correctAns.includes(a));

    let cardClass = isCorrect ? "is-correct" : (isAnswered ? "is-wrong" : "is-unanswered");
    let statusLabel = isCorrect ? "✓ Correct" : (isAnswered ? "✗ Incorrect" : "○ Unanswered");

    return `
      <div class="review-item-card ${cardClass}">
        <div style="display:flex; justify-content:space-between; margin-bottom:0.5rem;">
          <strong>Question ${i + 1} (${q.mark} Mark)</strong>
          <span style="font-weight:700;">${statusLabel}</span>
        </div>
        <div style="margin-bottom:0.75rem;">${q.promptHtml}</div>
        <div style="font-size:0.85rem; color:var(--text-secondary); margin-bottom:0.25rem;">Options:</div>
        <div>
          ${q.options.map((opt, oIdx) => {
            const isUserChoice = userAns.includes(oIdx);
            const isRightChoice = correctAns.includes(oIdx);

            let pillClass = "";
            let escapedText = escapeHtml(opt.text);
            let pillText = escapedText;

            if (isRightChoice) {
              pillClass = "correct-ans";
              pillText = `✓ [Correct Answer] ${escapedText}`;
            } else if (isUserChoice) {
              pillClass = "chosen-wrong";
              pillText = `✗ [Your Choice] ${escapedText}`;
            }

            return `<div class="review-opt-pill ${pillClass}">${pillText}</div>`;
          }).join("")}
        </div>
      </div>
    `;
  }).join("");
}

// ========================================================
// MODE 2: 1-QUESTION-AT-A-TIME PRACTICE CONTROLLER
// ========================================================
function initPracticeMode(mistakesOnly = false) {
  let filteredQuestions = [...AppState.questions];

  // Capture mistakes BEFORE clearing answers
  if (mistakesOnly) {
    const mistakesIndices = Object.keys(AppState.practice.answers)
      .filter(idx => !AppState.practice.answers[idx].isCorrect)
      .map(Number);
    if (mistakesIndices.length > 0) {
      filteredQuestions = AppState.questions.filter((_, i) => mistakesIndices.includes(i));
    }
  }

  AppState.practice.isMistakesOnly = mistakesOnly;
  AppState.practice.currentIndex = 0;
  AppState.practice.streak = 0;
  AppState.practice.bestStreak = 0;
  AppState.practice.answers = {};
  AppState.practice.multiPending = [];
  AppState.practice.questions = filteredQuestions;

  // Sync auto-advance button label
  const autoBtn = document.getElementById("btnPracticeAutoAdvance");
  if (autoBtn) {
    autoBtn.textContent = AppState.practice.autoAdvance ? "Auto-Next: ON ⚡" : "Auto-Next: OFF";
  }

  renderPracticeQuestion();
  showView("practice");
}

function renderPracticeQuestion() {
  const idx = AppState.practice.currentIndex;
  const q = AppState.practice.questions[idx];
  if (!q) {
    finishPractice();
    return;
  }

  // Progress Bar
  const total = AppState.practice.questions.length;
  const progressPercent = Math.round(((idx) / total) * 100);
  document.getElementById("practiceProgressFill").style.width = progressPercent + "%";
  document.getElementById("practiceProgressText").textContent = `Question ${idx + 1} of ${total}`;

  // Streak & Score
  document.getElementById("practiceStreakCount").textContent = AppState.practice.streak;

  // Question Info
  document.getElementById("practiceQModule").textContent = q.module;
  document.getElementById("practiceQPrompt").innerHTML = q.promptHtml;

  // Options
  const container = document.getElementById("practiceOptionsList");
  const answeredState = AppState.practice.answers[idx];

  container.innerHTML = q.options.map((opt, oIdx) => {
    let btnClass = "";
    let indicator = "";

    if (answeredState) {
      const isUserChoice = q.isMultiSelect
        ? (Array.isArray(answeredState.selected) && answeredState.selected.includes(oIdx))
        : (answeredState.selected === oIdx);
      const isRightChoice = q.correct.includes(oIdx);

      if (isRightChoice) {
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
    `;
  }).join("");

  // Feedback Banner
  const banner = document.getElementById("practiceFeedbackBanner");
  const nextBtn = document.getElementById("btnPracticeNext");

  if (answeredState) {
    banner.className = `feedback-banner ${answeredState.isCorrect ? 'correct' : 'wrong'}`;
    const correctOptTexts = q.correct.map(cIdx => escapeHtml(q.options[cIdx]?.text)).join(", ");
    banner.innerHTML = answeredState.isCorrect
      ? `<div class="feedback-banner-content"><strong>🎉 Spot on!</strong> That is the correct answer.</div>`
      : `<div class="feedback-banner-content"><strong>💡 Correct Answer:</strong> ${correctOptTexts}</div>`;
    banner.style.display = "flex";
    nextBtn.style.display = "inline-flex";
    nextBtn.textContent = (idx === AppState.practice.questions.length - 1) ? "Finish Practice" : "Next Question →";
  } else if (q.isMultiSelect && AppState.practice.multiPending.length > 0) {
    banner.style.display = "none";
    nextBtn.style.display = "inline-flex";
    nextBtn.textContent = "Check Answers ✓";
  } else {
    banner.style.display = "none";
    nextBtn.style.display = "none";
  }
}

function onPracticeOptionClick(optIdx) {
  const idx = AppState.practice.currentIndex;
  const q = AppState.practice.questions[idx];
  if (AppState.practice.answers[idx]) return;

  // Handle Multi-Select Questions
  if (q.isMultiSelect) {
    triggerHaptic(15);
    const pending = AppState.practice.multiPending;
    if (pending.includes(optIdx)) {
      AppState.practice.multiPending = pending.filter(i => i !== optIdx);
    } else {
      AppState.practice.multiPending = [...pending, optIdx];
    }
    renderPracticeQuestion();
    return;
  }

  // Single-Select Evaluation
  evaluatePracticeQuestion([optIdx]);
}

function evaluatePracticeQuestion(selectedIndices) {
  const idx = AppState.practice.currentIndex;
  const q = AppState.practice.questions[idx];

  const correctAnswers = q.correct;
  const isExactMatch = (selectedIndices.length === correctAnswers.length) &&
    selectedIndices.every(ans => correctAnswers.includes(ans));

  if (isExactMatch) {
    triggerHaptic(20);
    AppState.practice.streak++;
    if (AppState.practice.streak > AppState.practice.bestStreak) {
      AppState.practice.bestStreak = AppState.practice.streak;
    }
  } else {
    triggerHaptic(60);
    AppState.practice.streak = 0;
  }

  AppState.practice.answers[idx] = {
    selected: q.isMultiSelect ? selectedIndices : selectedIndices[0],
    isCorrect: isExactMatch
  };
  AppState.practice.multiPending = [];

  renderPracticeQuestion();

  // Auto Advance if enabled and answer is correct
  if (isExactMatch && AppState.practice.autoAdvance) {
    AppState.practice.autoAdvanceTimeout = setTimeout(() => {
      onPracticeNext();
    }, 1200);
  }
}

function onPracticeNext() {
  triggerHaptic(10);
  if (AppState.practice.autoAdvanceTimeout) {
    clearTimeout(AppState.practice.autoAdvanceTimeout);
    AppState.practice.autoAdvanceTimeout = null;
  }

  const idx = AppState.practice.currentIndex;
  const q = AppState.practice.questions[idx];

  // If multi-select is pending evaluation
  if (q && q.isMultiSelect && !AppState.practice.answers[idx] && AppState.practice.multiPending.length > 0) {
    evaluatePracticeQuestion(AppState.practice.multiPending);
    return;
  }

  if (AppState.practice.currentIndex < AppState.practice.questions.length - 1) {
    AppState.practice.currentIndex++;
    AppState.practice.multiPending = [];
    renderPracticeQuestion();
  } else {
    finishPractice();
  }
}

function togglePracticeShuffle() {
  triggerHaptic(10);
  AppState.practice.questions = AppState.practice.questions.sort(() => Math.random() - 0.5);
  AppState.practice.currentIndex = 0;
  AppState.practice.answers = {};
  AppState.practice.multiPending = [];
  renderPracticeQuestion();
  showToast("Questions shuffled!");
}

function togglePracticeAutoAdvance() {
  AppState.practice.autoAdvance = !AppState.practice.autoAdvance;
  const btn = document.getElementById("btnPracticeAutoAdvance");
  if (btn) {
    btn.textContent = AppState.practice.autoAdvance ? "Auto-Next: ON ⚡" : "Auto-Next: OFF";
  }
  showToast(AppState.practice.autoAdvance ? "Auto-advance enabled" : "Auto-advance disabled");
}

function finishPractice() {
  const answeredTotal = Object.keys(AppState.practice.answers).length;
  const correctCount = Object.values(AppState.practice.answers).filter(a => a.isCorrect).length;
  const accuracy = answeredTotal > 0 ? Math.round((correctCount / answeredTotal) * 100) : 0;

  document.getElementById("practiceSummaryScore").textContent = `${correctCount} / ${answeredTotal}`;
  document.getElementById("practiceSummaryAccuracy").textContent = accuracy + "%";
  document.getElementById("practiceSummaryStreak").textContent = AppState.practice.bestStreak;

  const mistakesCount = answeredTotal - correctCount;
  const btnMistakes = document.getElementById("btnPracticeReviewMistakes");
  if (btnMistakes) {
    btnMistakes.style.display = mistakesCount > 0 ? "inline-flex" : "none";
  }

  showView("practice_summary");
}

function practiceMistakesOnly() {
  initPracticeMode(true);
}

// Exit Modal Handlers
function openExitModal(customTitle, customDesc, onExitCallback) {
  triggerHaptic(10);
  const titleEl = document.getElementById("exitModalTitle");
  const descEl = document.getElementById("exitModalDescription");
  if (titleEl && customTitle) titleEl.textContent = customTitle;
  if (descEl && customDesc) descEl.textContent = customDesc;
  AppState._exitCallback = onExitCallback;
  const modal = document.getElementById("exitConfirmModal");
  if (modal) modal.classList.add("open");
}

function closeExitModal() {
  const modal = document.getElementById("exitConfirmModal");
  if (modal) modal.classList.remove("open");
  AppState._exitCallback = null;
}

function onConfirmExitExam() {
  const cb = AppState._exitCallback;
  closeExitModal();
  if (typeof cb === "function") {
    cb();
  } else {
    clearInterval(AppState.portal.timerInterval);
    showView("catalog");
  }
}

// Android Back Navigation
function handleBack() {
  if (AppState.practice.autoAdvanceTimeout) {
    clearTimeout(AppState.practice.autoAdvanceTimeout);
    AppState.practice.autoAdvanceTimeout = null;
  }

  // 1. Close modals or drawers if open
  const exitModal = document.getElementById("exitConfirmModal");
  if (exitModal && exitModal.classList.contains("open")) {
    closeExitModal();
    return;
  }

  const submitModal = document.getElementById("submitConfirmModal");
  if (submitModal && submitModal.classList.contains("open")) {
    closeSubmitModal();
    return;
  }

  const pickerModal = document.getElementById("examPickerModal");
  if (pickerModal && pickerModal.classList.contains("open")) {
    closePickerModal();
    return;
  }

  const paletteDrawer = document.getElementById("paletteDrawer");
  if (paletteDrawer && paletteDrawer.classList.contains("open")) {
    togglePaletteDrawer();
    return;
  }

  // 2. Handle Views
  if (AppState.currentView === "portal") {
    openExitModal(
      "Exit Examination?",
      "Are you sure you want to exit the examination? Your exam progress and answers in this session will be lost.",
      () => {
        clearInterval(AppState.portal.timerInterval);
        showView("catalog");
      }
    );
  } else if (AppState.currentView === "practice") {
    const answeredCount = Object.keys(AppState.practice.answers).length;
    if (answeredCount > 0) {
      openExitModal(
        "Exit Practice?",
        "Are you sure you want to exit this practice session? Your current streak and session progress will be reset.",
        () => {
          showView("catalog");
        }
      );
    } else {
      showView("catalog");
    }
  } else if (AppState.currentView !== "catalog") {
    showView("catalog");
  } else {
    if (window.AndroidBridge && typeof window.AndroidBridge.exitPortal === "function") {
      window.AndroidBridge.exitPortal();
    } else {
      showToast("You are already on the Exam Catalog.");
    }
  }
}
