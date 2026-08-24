import { Eye, EyeOff, LockKeyhole, WalletCards } from 'lucide-react';
import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const { login } = useAuth();
  const location = useLocation();
  const [form, setForm] = useState({ username: '', password: '' });
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const submit = async (event) => { event.preventDefault(); setError(''); setLoading(true); try { await login(form.username, form.password); } catch (requestError) { setError(requestError.userMessage || 'Usuário ou senha inválidos.'); } finally { setLoading(false); } };
  return <div className="login-page"><div className="login-aside"><div className="login-brand"><div className="brand-mark">K</div><strong>KASH</strong></div><div><p className="eyebrow">clareza para o seu dinheiro</p><h1>Seu dinheiro.<br /><em>Mais simples.</em></h1><p className="login-intro">Acompanhe sua vida financeira com decisões mais tranquilas e uma visão que faz sentido.</p></div><div className="login-stat"><WalletCards size={20} /><span>Controle diário, sem complicação</span></div></div><main className="login-card"><div className="login-card-inner"><div className="login-heading"><div className="mobile-brand-mark">K</div><p className="eyebrow">bem-vindo de volta</p><h2>Entrar no KashDuv</h2><p>Use suas credenciais para acessar sua conta.</p></div>{location.state?.registered && <p className="success-message" role="status">Conta criada. Agora entre com seus dados.</p>}<form onSubmit={submit} noValidate><label htmlFor="username">Usuário</label><input id="username" name="username" autoComplete="username" value={form.username} onChange={(event) => setForm({ ...form, username: event.target.value })} required autoFocus /><label htmlFor="password">Senha</label><div className="password-field"><LockKeyhole size={17} /><input id="password" name="password" type={showPassword ? 'text' : 'password'} autoComplete="current-password" value={form.password} onChange={(event) => setForm({ ...form, password: event.target.value })} required /><button type="button" className="icon-button" onClick={() => setShowPassword(!showPassword)} aria-label={showPassword ? 'Ocultar senha' : 'Mostrar senha'}>{showPassword ? <EyeOff size={18} /> : <Eye size={18} />}</button></div>{error && <p className="field-error" role="alert">{error}</p>}<button className="primary-button login-submit" type="submit" disabled={loading}>{loading ? <span className="spinner" /> : 'Entrar na conta'}</button></form><p className="login-footnote">Ainda não possui conta? <Link className="text-link" to="/cadastro">Criar conta</Link></p></div></main></div>;
}
