const BASE = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

async function handleResponse(res) {
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Request failed");
  }
  return res.json();
}

export async function fetchAbilities(style) {
  const res = await fetch(`${BASE}/abilities?style=${style}`);
  return handleResponse(res);
}

export async function fetchBatchCalculation(payload) {
  const res = await fetch(`${BASE}/damage/calculate/batch`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

export async function fetchDetailedAbilityCalculation(payload) {
  const res = await fetch(`${BASE}/damage/calculate`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

export async function fetchEquipmentBySlot(slot, query) {
  const res = await fetch(
    `${BASE}/equipment/search?q=${encodeURIComponent(query)}&slot=${slot}`,
  );
  return res.json();
}

export async function fetchBuffs() {
  const res = await fetch(`${BASE}/buffs`);
  return handleResponse(res);
}

export async function fetchSpells(query) {
  const res = await fetch(`${BASE}/spells?q=${encodeURIComponent(query)}`);
  return handleResponse(res);
}

export async function fetchPrayers() {
  const res = await fetch(`${BASE}/prayers`);
  return handleResponse(res);
}

export async function fetchPrayersByName(query) {
  const res = await fetch(`${BASE}/prayers?q=${encodeURIComponent(query)}`);
  return handleResponse(res);
}

export async function fetchPerks() {
  const res = await fetch(`${BASE}/perks`);
  return handleResponse(res);
}

export async function fetchTargets(query) {
  const res = await fetch(
    `${BASE}/target/search?q=${encodeURIComponent(query)}`,
  );
  return handleResponse(res);
}

export async function fetchFamiliars() {
  const res = await fetch(`${BASE}/familiars`);
  return handleResponse(res);
}

export async function fetchPotions() {
  const res = await fetch(`${BASE}/potions`);
  return handleResponse(res);
}

export async function fetchEquipmentByIds(ids) {
  const res = await fetch(`${BASE}/equipment/by-ids`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(ids),
  });
  return handleResponse(res);
}

export async function fetchTargetByTitle(title) {
  const res = await fetch(
    `${BASE}/target/by-title?title=${encodeURIComponent(title)}`,
  );

  return handleResponse(res);
}
