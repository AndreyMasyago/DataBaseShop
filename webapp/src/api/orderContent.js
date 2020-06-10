import { API_BASE } from './config.js';

export async function listOrderContent() {
  const r = await fetch(`${API_BASE}/orderContent/`);
  return await r.json();
}

export async function createOrderContent(data) {
  const r = await fetch(`${API_BASE}/orderContent/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getOrderContent(id) {
  const r = await fetch(`${API_BASE}/orderContent/${id}/`);
  return await r.json();  
}

export async function deleteOrderContent(id) {
  await fetch(`${API_BASE}/orderContent/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateOrderContent(data) {
  const r = await fetch(`${API_BASE}/orderContent/${data.orderContentId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}