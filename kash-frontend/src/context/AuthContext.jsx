import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState(() => sessionStorage.getItem('kash.credentials'));
  const username = credentials ? atob(credentials).split(':')[0] : '';

  useEffect(() => {
    const logoutOnUnauthorized = () => { setCredentials(null); navigate('/login', { replace: true }); };
    window.addEventListener('kash:unauthorized', logoutOnUnauthorized);
    return () => window.removeEventListener('kash:unauthorized', logoutOnUnauthorized);
  }, [navigate]);

  const login = async (user, password) => {
    const encoded = btoa(`${user}:${password}`);
    sessionStorage.setItem('kash.credentials', encoded);
    try {
      await api.get('/despesas?size=1');
      setCredentials(encoded);
      navigate('/dashboard', { replace: true });
    } catch (error) {
      sessionStorage.removeItem('kash.credentials');
      throw error;
    }
  };

  const logout = () => { sessionStorage.removeItem('kash.credentials'); setCredentials(null); navigate('/login', { replace: true }); };
  const value = { credentials, username, isAuthenticated: Boolean(credentials), login, logout };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() { return useContext(AuthContext); }
