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

export async function getMonthlyAverageSales(params) {
  const r = await fetch(
  	`${API_BASE}/order-content/monthly-average-sales/?goodsSearch=${params.goodsSearch}`);
  return await r.json();
}

export async function getFinanceReport(params) {
  const r = await fetch(
  	`${API_BASE}/goods/finance-report/?startDate=${params.startDate}&endDate=${params.endDate}`);
  return await r.json();
}

export async function getProviderIncomeStats(params) {
  const r = await fetch(
  	`${API_BASE}/order-content/provider-income-stats/?providerSearch=${params.providerSearch}`);
  return await r.json();
}

export async function getOverHead() {
  const r = await fetch(
    `${API_BASE}/order-content/overhead/`);
  return await r.json();
}

export async function getDailyReport(params) {
  const r = await fetch(
    `${API_BASE}/goods/daily-report/?reportDate=${params.reportDate}`);
  return await r.json();
}

export async function getStorageReport() {
  const r = await fetch(
    `${API_BASE}/goods/storage-report/`);
  return await r.json();
}

export async function getDeliveredMoreThanCount(param) {
  const r = await fetch(
    `${API_BASE}/provider/delivered-more-than-count/?goodsSearch=${param.goodsSearch}&categorySearch=${param.categorySearch}&amountLimit=${param.amountLimit}&startDate=${param.startDate}&endDate=${param.endDate}`);
  return await r.json();
}
