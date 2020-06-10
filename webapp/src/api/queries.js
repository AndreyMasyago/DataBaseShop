import { API_BASE } from './config.js';

export async function getBestSellers() {
  const r = await fetch(`${API_BASE}/goods/bestsellers/`);
  return await r.json();
}

export async function getGoodsDetails(params) {
  const r = await fetch(`${API_BASE}/goods/goods-details/?goodsSearch=${params.goodsSearch}`);
  return await r.json();
}

export async function getRejectDetails() {
  const r = await fetch(`${API_BASE}/goods-rejects/`);
  return await r.json();
}


export async function getProvidersWithReject() {
  const r = await fetch(`${API_BASE}/reject-providers/`);
  return await r.json();
}
