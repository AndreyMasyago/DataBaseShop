import { API_BASE } from './config.js';

export async function listRejects() {
  const r = await fetch(`${API_BASE}/reject/`);
  return await r.json();
}

export async function createReject(data) {
  const r = await fetch(`${API_BASE}/reject/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getReject(id) {
  const r = await fetch(`${API_BASE}/reject/${id}/`);
  return await r.json();  
}

export async function deleteReject(id) {
  await fetch(`${API_BASE}/reject/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateReject(data) {
  const r = await fetch(`${API_BASE}/reject/${data.rejectId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}