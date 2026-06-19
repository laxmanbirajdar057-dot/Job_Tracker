// Referral Network tab + the referral picker used inside the Job modal.
// A "referral" here is a CONTACT (someone who can refer you), independent
// of any specific job. A job can optionally be linked to one referral
// contact via referralId, which is what makes it show up as "Referred"
// instead of "No referral" on the Applications tab.

const RELATIONSHIP_VALUES = [
  "COLLEGE_ALUMNI",
  "FORMER_COLLEAGUE",
  "CURRENT_COLLEAGUE",
  "FRIEND",
  "FAMILY",
  "LINKEDIN_CONNECTION",
  "RECRUITER",
  "OTHER",
];

let allReferrals = [];
let editingReferralId = null;

document.addEventListener("DOMContentLoaded", () => {
  if (!Auth.isLoggedIn()) return;

  // Tab switching (Applications <-> Referral Network)
  document.querySelectorAll(".tab-btn").forEach((btn) => {
    btn.addEventListener("click", () => switchTab(btn.dataset.tab));
  });

  document.getElementById("addReferralBtn").addEventListener("click", () => openReferralModal());
  document.getElementById("quickAddReferralBtn").addEventListener("click", () => openReferralModal());
  document.getElementById("referralForm").addEventListener("submit", handleSaveReferral);
  document.getElementById("referralCancelBtn").addEventListener("click", closeReferralModal);
  document.getElementById("referralModalOverlay").addEventListener("click", (e) => {
    if (e.target.id === "referralModalOverlay") closeReferralModal();
  });

  loadReferrals();
  loadReferralStats();
});

function switchTab(tab) {
  document.querySelectorAll(".tab-btn").forEach((b) => b.classList.toggle("active", b.dataset.tab === tab));
  document.getElementById("tab-applications").classList.toggle("active", tab === "applications");
  document.getElementById("tab-network").classList.toggle("active", tab === "network");
}

async function loadReferrals() {
  try {
    const response = await apiFetch("/referrals");
    allReferrals = response.data || [];
    renderReferralNetwork(allReferrals);
    populateJobReferralSelect();
  } catch (err) {
    const container = document.getElementById("referralList");
    if (container) {
      container.innerHTML = `<div class="empty-state">Couldn't load referral contacts: ${escapeHtml(err.message)}</div>`;
    }
  }
}

async function loadReferralStats() {
  try {
    const stats = await apiFetch("/referrals/stats");
    document.getElementById("totalReferralsCount").textContent = stats.totalReferrals ?? 0;
    document.getElementById("totalViaReferralCount").textContent = stats.totalApplicationsViaReferral ?? 0;
    const rate = typeof stats.referralRatePercent === "number" ? stats.referralRatePercent : 0;
    document.getElementById("referralRatePercent").textContent = `${Math.round(rate)}%`;
  } catch (err) {
    console.error("Failed to load referral stats", err);
  }
}

function renderReferralNetwork(referrals) {
  const container = document.getElementById("referralList");
  if (!container) return;

  if (referrals.length === 0) {
    container.innerHTML = `
      <div class="empty-state">
        <h3>No referral contacts yet</h3>
        <p>Add the people in your network who can refer you, then link them to a job application.</p>
      </div>`;
    return;
  }

  container.innerHTML = referrals
    .map((ref) => {
      const initials = (ref.referrerName || "?")
        .split(" ")
        .map((p) => p[0])
        .filter(Boolean)
        .slice(0, 2)
        .join("")
        .toUpperCase();

      return `
        <div class="referral-card" data-id="${ref.id}">
          <div class="referral-card-head">
            <div class="referral-avatar">${escapeHtml(initials)}</div>
            <div>
              <h3>${escapeHtml(ref.referrerName)}</h3>
              ${ref.relationship ? `<div class="referral-relation">${formatLabel(ref.relationship)}</div>` : ""}
            </div>
          </div>
          <div class="referral-meta">
            ${ref.company ? `<span>🏢 ${escapeHtml(ref.company)}</span>` : ""}
            ${ref.referrerEmail ? `<span>✉️ ${escapeHtml(ref.referrerEmail)}</span>` : ""}
            ${ref.referrerPhone ? `<span>📞 ${escapeHtml(ref.referrerPhone)}</span>` : ""}
            ${ref.referrerLinkedinUrl ? `<span>🔗 <a href="${escapeHtml(ref.referrerLinkedinUrl)}" target="_blank" rel="noopener">LinkedIn</a></span>` : ""}
          </div>
          <span class="referral-job-count">${ref.referredJobCount || 0} job${ref.referredJobCount === 1 ? "" : "s"} linked</span>
          <div class="referral-card-actions">
            <button class="btn btn-secondary btn-sm" onclick="openReferralModal(${ref.id})">Edit</button>
            <button class="btn btn-danger btn-sm" onclick="handleDeleteReferral(${ref.id})">Delete</button>
          </div>
        </div>`;
    })
    .join("");
}

function openReferralModal(referralId = null) {
  editingReferralId = referralId;
  const form = document.getElementById("referralForm");
  form.reset();

  const relationshipSelect = document.getElementById("relationship");
  relationshipSelect.innerHTML =
    `<option value="">Select relationship</option>` +
    RELATIONSHIP_VALUES.map((v) => `<option value="${v}">${formatLabel(v)}</option>`).join("");

  document.getElementById("referralModalTitle").textContent = referralId ? "Edit Referral Contact" : "Add Referral Contact";

  if (referralId) {
    const ref = allReferrals.find((r) => r.id === referralId);
    if (ref) {
      document.getElementById("referrerName").value = ref.referrerName || "";
      document.getElementById("relationship").value = ref.relationship || "";
      document.getElementById("referralCompany").value = ref.company || "";
      document.getElementById("referrerEmail").value = ref.referrerEmail || "";
      document.getElementById("referrerPhone").value = ref.referrerPhone || "";
      document.getElementById("referrerLinkedinUrl").value = ref.referrerLinkedinUrl || "";
      document.getElementById("referralNotes").value = ref.notes || "";
    }
  }

  document.getElementById("referralModalOverlay").classList.add("visible");
}

function closeReferralModal() {
  document.getElementById("referralModalOverlay").classList.remove("visible");
  editingReferralId = null;
}

async function handleSaveReferral(event) {
  event.preventDefault();
  const saveBtn = document.getElementById("referralSaveBtn");

  const payload = {
    referrerName: document.getElementById("referrerName").value.trim(),
    relationship: document.getElementById("relationship").value || null,
    company: document.getElementById("referralCompany").value.trim() || null,
    referrerEmail: document.getElementById("referrerEmail").value.trim() || null,
    referrerPhone: document.getElementById("referrerPhone").value.trim() || null,
    referrerLinkedinUrl: document.getElementById("referrerLinkedinUrl").value.trim() || null,
    notes: document.getElementById("referralNotes").value.trim() || null,
  };

  if (!payload.referrerName) {
    showToast("Referrer name is required.", "error");
    return;
  }

  setButtonLoading(saveBtn, true, "Saving...");

  try {
    if (editingReferralId) {
      await apiFetch(`/referrals/${editingReferralId}`, {
        method: "PATCH",
        body: JSON.stringify(payload),
      });
      showToast("Referral contact updated.");
    } else {
      await apiFetch("/referrals", {
        method: "POST",
        body: JSON.stringify(payload),
      });
      showToast("Referral contact added.");
    }

    closeReferralModal();
    await loadReferrals();
    await loadReferralStats();
    if (typeof loadDashboard === "function") loadDashboard();
  } catch (err) {
    showToast(err.message || "Failed to save referral contact.", "error");
  } finally {
    setButtonLoading(saveBtn, false);
  }
}

async function handleDeleteReferral(referralId) {
  if (!confirm("Delete this referral contact? Any jobs linked to them will be unlinked, not deleted.")) return;

  try {
    await apiFetch(`/referrals/${referralId}`, { method: "DELETE" });
    showToast("Referral contact deleted.");
    await loadReferrals();
    await loadReferralStats();
    if (typeof loadDashboard === "function") loadDashboard();
  } catch (err) {
    showToast(err.message || "Failed to delete referral contact.", "error");
  }
}

// Used by jobs.js to populate the "Referral contact" dropdown inside the
// Add/Edit Job modal, and to look up a referral's display info for badges.

function populateJobReferralSelect(selectedId = "") {
  const select = document.getElementById("referralId");
  if (!select) return;

  select.innerHTML =
    `<option value="">No referral for this application</option>` +
    allReferrals
      .map((ref) => {
        const label = [ref.referrerName, ref.relationship ? formatLabel(ref.relationship) : null, ref.company ? `@ ${ref.company}` : null]
          .filter(Boolean)
          .join(" — ");
        return `<option value="${ref.id}">${escapeHtml(label)}</option>`;
      })
      .join("");

  select.value = selectedId || "";
}

function getReferralById(id) {
  if (!id) return null;
  return allReferrals.find((r) => r.id === id) || null;
}