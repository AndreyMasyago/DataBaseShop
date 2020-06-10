import { API_BASE } from './config.js';

export async function listCells() {
  const r = await fetch(`${API_BASE}/storage/`);
  return await r.json();
}

export async function createCell(data) {
  const r = await fetch(`${API_BASE}/storage/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getCell(id) {
  const r = await fetch(`${API_BASE}/storage/${id}/`);
  return await r.json();  
}

export async function deleteCell(id) {
  await fetch(`${API_BASE}/storage/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateCell(data) {
  const r = await fetch(`${API_BASE}/storage/${data.cellsId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}