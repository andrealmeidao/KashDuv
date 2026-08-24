import { BrowserRouter, Navigate, Outlet, Route, Routes } from 'react-router-dom';
import AppLayout from './components/AppLayout';
import { AuthProvider, useAuth } from './context/AuthContext';
import DashboardPage from './pages/DashboardPage';
import FinancePage from './pages/FinancePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import SummaryPage from './pages/SummaryPage';
import './index.css';

export default function App() {
  return <BrowserRouter><AuthProvider><Routes><Route path="/login" element={<LoginPage />} /><Route path="/cadastro" element={<RegisterPage />} /><Route element={<ProtectedRoute />}><Route element={<AppLayout />}><Route path="/dashboard" element={<DashboardPage />} /><Route path="/despesas" element={<FinancePage type="despesas" />} /><Route path="/receitas" element={<FinancePage type="receitas" />} /><Route path="/resumo" element={<SummaryPage />} /><Route path="*" element={<Navigate to="/dashboard" replace />} /></Route></Route></Routes></AuthProvider></BrowserRouter>;
}

function ProtectedRoute() {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
}
