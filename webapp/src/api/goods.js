import { API_BASE } from './config.js';

export async function listGoods() {
  const r = await fetch(`${API_BASE}/goods/`);
  return await r.json();
}

export async function createGoods(data) {
  const r = await fetch(`${API_BASE}/goods/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getGoods(id) {
  const r = await fetch(`${API_BASE}/goods/${id}/`);
  return await r.json();  
}

export async function deleteGoods(id) {
  await fetch(`${API_BASE}/goods/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateGoods(data) {
  const r = await fetch(`${API_BASE}/goods/${data.goodsId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}
