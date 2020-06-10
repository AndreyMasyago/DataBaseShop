import { API_BASE } from './config.js';

export async function listDetails() {
  const r = await fetch(`${API_BASE}/catalog/`);
  return await r.json();
}

export async function createDetail(data) {
  const r = await fetch(`${API_BASE}/catalog/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getDetail(id) {
  const r = await fetch(`${API_BASE}/catalog/${id}/`);
  return await r.json();  
}

export async function deleteDetail(id) {
  await fetch(`${API_BASE}/catalog/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateDetail(data) {
  const r = await fetch(`${API_BASE}/catalog/${data.detailId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}