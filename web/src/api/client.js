import axios from 'axios';
import qs from 'qs';

const client = axios.create({ baseURL: '/api', timeout: 10000, paramsSerializer: params => qs.stringify(params, { arrayFormat: 'repeat' }) });

export function withToken(token) {
  client.defaults.headers.common.Authorization = token ? `Bearer ${ token }` : undefined;
  return client;
}

export async function fetchFindings(token, search) {
  const { data } = await withToken(token).get('/findings', { params: { q: search } });
  return data.content ?? data;
}

export async function login(username, password) {
  const { data } = await client.post(`/users/${ encodeURIComponent(username) }/token`, { password });
  return data.token;
}

export async function renderReport(token, template, tenant) {
  const form = new FormData();
  form.append('template', new Blob([ template ], { type: 'text/yaml' }));
  const { data } = await withToken(token).post('/public/reports/render', form, { params: { tenant } });
  return data;
}
