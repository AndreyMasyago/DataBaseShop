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
  const query = Object.keys(params).reduce((q, k) => {
    q.push(`${k}=${params[k]}`);
    return q;
  }, []).join('&');
  console.log(query);

  const r = await fetch(
  	`${API_BASE}/order-content/monthly-average-sales-2/?${query}`);
  return await r.json();
}

export async function getFinanceReport(params) {
  const r = await fetch(
  	`${API_BASE}/goods/finance-report/?startDate=${params.startDate}&endDate=${params.endDate}`);
  return await r.json();
}

export async function getProviderIncomeStats(params) {
  const r = await fetch(
  	`${API_BASE}/order-content/provider-income-stats/?providerSearch=${params.providerSearch}&orderDateFrom=${params.orderDateFrom}&orderDateTo=${params.orderDateTo}`);
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

export async function getDeliveredMoreThanCount(params) {
  const r = await fetch(
    `${API_BASE}/provider/delivered-more-than-count/?goodsSearch=${params.goodsSearch}&categorySearch=${params.categorySearch}&amountLimit=${params.amountLimit}&startDate=${params.startDate}&endDate=${params.endDate}`);
  return await r.json();
}

export async function addDelivery(data) {
  const r = await fetch(`${API_BASE}/delivery/addDelivery`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(data)
  });
  if (r.status === 200) {
    return await r.json();
  } else {
    return { errors: await r.json() };
  }
}

export async function getOrdersWithAmountFiltredByDate(param) {
  const r = await fetch(
    `${API_BASE}/order-content/order-content-by-date/?goodsSearch=${param.goodsSearch}&amountLimit=${param.amountLimit}&orderDateFrom=${param.orderDateFrom}&orderDateTo=${param.orderDateTo}`);
  return await r.json();
}
