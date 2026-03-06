const BASE = "/api";

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
