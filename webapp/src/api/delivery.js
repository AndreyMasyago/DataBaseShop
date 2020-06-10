import { API_BASE } from './config.js';

export async function listDeliveries() {
  const r = await fetch(`${API_BASE}/delivery/`);
  return await r.json();
}

export async function createDelivery(data) {
  const r = await fetch(`${API_BASE}/delivery/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getDelivery(id) {
  const r = await fetch(`${API_BASE}/delivery/${id}/`);
  return await r.json();  
}

export async function deleteDelivery(id) {
  await fetch(`${API_BASE}/delivery/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateDelivery(data) {
  const r = await fetch(`${API_BASE}/delivery/${data.deliveryId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}