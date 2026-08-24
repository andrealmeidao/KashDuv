import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE || '/api',
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use((config) => {
  const credentials = sessionStorage.getItem('kash.credentials');
  if (credentials) config.headers.Authorization = `Basic ${credentials}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      sessionStorage.removeItem('kash.credentials');
      window.dispatchEvent(new Event('kash:unauthorized'));
    }
    const message = error.response?.data?.mensagem || (error.response ? `Erro ${error.response.status}. Tente novamente.` : 'Não foi possível conectar ao servidor. Tente novamente.');
    return Promise.reject(Object.assign(error, { userMessage: message }));
  },
);

export default api;
