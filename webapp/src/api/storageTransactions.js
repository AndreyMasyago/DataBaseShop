import { API_BASE } from './config.js';

export async function listStorageTransactions() {
  const r = await fetch(`${API_BASE}/storageTransactions/`);
  return await r.json();
}

export async function createStorageTransaction(data) {
  const r = await fetch(`${API_BASE}/storageTransactions/`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}

export async function getStorageTransaction(id) {
  const r = await fetch(`${API_BASE}/storageTransactions/${id}/`);
  return await r.json();  
}

export async function deleteStorageTransaction(id) {
  await fetch(`${API_BASE}/storageTransactions/${id}/`, {
    method: 'DELETE'
  });
}

export async function updateStorageTransaction(data) {
  const r = await fetch(`${API_BASE}/storageTransactions/${data.storageTransactionId}/`, {
    method: 'PUT',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  return await r.json();
}