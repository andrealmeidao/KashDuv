import { BarChart3, ChevronLeft, ChevronRight, CircleDollarSign, LayoutDashboard, LogOut, Menu, ReceiptText, X } from 'lucide-react';
import { NavLink, Outlet } from 'react-router-dom';
import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

const links = [
  { to: '/dashboard', label: 'Dashboard', icon: LayoutDashboard },
  { to: '/despesas', label: 'Despesas', icon: ReceiptText },
  { to: '/receitas', label: 'Receitas', icon: CircleDollarSign },
  { to: '/resumo', label: 'Resumo', icon: BarChart3 },
];

export default function AppLayout() {
  const [open, setOpen] = useState(false);
  const [collapsed, setCollapsed] = useState(false);
  const { username, logout } = useAuth();
  return <div className={`app-shell ${collapsed ? 'sidebar-collapsed' : ''}`}>
    <aside className={`sidebar ${open ? 'sidebar-open' : ''}`}>
      <div className="brand"><div className="brand-mark">K</div><div className="brand-copy"><strong>KASH</strong><span>finanças pessoais</span></div><button className="icon-button mobile-only" onClick={() => setOpen(false)} aria-label="Fechar menu"><X size={20} /></button></div>
      <nav className="main-nav" aria-label="Navegação principal">{links.map(({ to, label, icon: Icon }) => <NavLink key={to} to={to} onClick={() => setOpen(false)} className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}><Icon size={19} /><span>{label}</span></NavLink>)}</nav>
      <div className="sidebar-footer"><div className="user-mini"><span className="avatar">{username.slice(0, 1).toUpperCase()}</span><span className="user-name">{username}</span></div><button className="nav-link logout-link" onClick={logout}><LogOut size={19} /><span>Sair</span></button></div>
      <button className="collapse-button desktop-only" onClick={() => setCollapsed(!collapsed)} aria-label={collapsed ? 'Expandir menu' : 'Recolher menu'}>{collapsed ? <ChevronRight size={18} /> : <ChevronLeft size={18} />}</button>
    </aside>
    <div className="page-area"><header className="topbar"><button className="icon-button mobile-only" onClick={() => setOpen(true)} aria-label="Abrir menu"><Menu size={21} /></button><div className="topbar-context">Visão geral <span>/</span> <strong>KashDuv</strong></div><div className="topbar-user"><span className="status-dot" />{username}</div></header><main className="content"><Outlet /></main></div>
  </div>;
}
