import { API_BASE } from './config.js';

export async function listOrderEntity() {
  const r = await fetch(`${API_BASE}/order/`);
  return await r.json();
}

export async function createOrderEntity(data) {
  const r = await fetch(`${API_BASE}/order/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getOrderEntity(id) {
  const r = await fetch(`${API_BASE}/order/${id}/`);
  return await r.json();  
}

export async function deleteOrderEntity(id) {
  await fetch(`${API_BASE}/order/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateOrderEntity(data) {
  const r = await fetch(`${API_BASE}/order/${data.orderId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}