import { API_BASE } from './config.js';

export async function listProviders() {
  const r = await fetch(`${API_BASE}/provider/`);
  return await r.json();
}

export async function createProvider(data) {
  const r = await fetch(`${API_BASE}/provider/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getProvider(id) {
  const r = await fetch(`${API_BASE}/provider/${id}/`);
  return await r.json();  
}

export async function deleteProvider(id) {
  await fetch(`${API_BASE}/provider/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateProvider(data) {
  const r = await fetch(`${API_BASE}/provider/${data.providerId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}