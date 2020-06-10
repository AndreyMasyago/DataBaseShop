import { API_BASE } from './config.js';

export async function listDeliveryContent() {
  const r = await fetch(`${API_BASE}/deliveryContent/`);
  return await r.json();
}

export async function createDeliveryContent(data) {
  const r = await fetch(`${API_BASE}/deliveryContent/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getDeliveryContent(id) {
  const r = await fetch(`${API_BASE}/deliveryContent/${id}/`);
  return await r.json();  
}

export async function deleteDeliveryContent(id) {
  await fetch(`${API_BASE}/deliveryContent/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateDeliveryContent(data) {
  const r = await fetch(`${API_BASE}/deliveryContent/${data.deliveryContentId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}