import { Eye, EyeOff, LockKeyhole, UserPlus } from 'lucide-react';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', password: '', confirmation: '' });
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const submit = async (event) => {
    event.preventDefault(); setError('');
    if (form.password.length < 6) { setError('A senha precisa ter pelo menos 6 caracteres.'); return; }
    if (form.password !== form.confirmation) { setError('As senhas não conferem.'); return; }
    setLoading(true);
    try { await api.post('/auth/registrar', { username: form.username.trim(), password: form.password }); navigate('/login', { state: { registered: true } }); }
    catch (requestError) { setError(requestError.userMessage || 'Não foi possível criar a conta. Escolha outro usuário.'); }
    finally { setLoading(false); }
  };
  return <div className="login-page"><div className="login-aside"><div className="login-brand"><div className="brand-mark">K</div><strong>KASH</strong></div><div><p className="eyebrow">comece hoje</p><h1>Uma visão<br /><em>mais leve.</em></h1><p className="login-intro">Crie sua conta e transforme seus lançamentos em decisões mais claras.</p></div><div className="login-stat"><UserPlus size={20} /><span>Cadastro rápido e seguro</span></div></div><main className="login-card"><div className="login-card-inner"><div className="login-heading"><div className="mobile-brand-mark">K</div><p className="eyebrow">primeiro passo</p><h2>Crie sua conta</h2><p>Preencha os dados para começar a organizar suas finanças.</p></div><form onSubmit={submit} noValidate><label htmlFor="register-username">Usuário</label><input id="register-username" autoComplete="username" value={form.username} onChange={(event) => setForm({ ...form, username: event.target.value })} required autoFocus /><label htmlFor="register-password">Senha</label><div className="password-field"><LockKeyhole size={17} /><input id="register-password" type={showPassword ? 'text' : 'password'} autoComplete="new-password" value={form.password} onChange={(event) => setForm({ ...form, password: event.target.value })} required /><button type="button" className="icon-button" onClick={() => setShowPassword(!showPassword)} aria-label={showPassword ? 'Ocultar senha' : 'Mostrar senha'}>{showPassword ? <EyeOff size={18} /> : <Eye size={18} />}</button></div><label htmlFor="register-confirmation">Confirmar senha</label><input id="register-confirmation" type={showPassword ? 'text' : 'password'} autoComplete="new-password" value={form.confirmation} onChange={(event) => setForm({ ...form, confirmation: event.target.value })} required />{error && <p className="field-error" role="alert">{error}</p>}<button className="primary-button login-submit" type="submit" disabled={loading}>{loading ? <span className="spinner" /> : 'Criar minha conta'}</button></form><p className="login-footnote">Já possui uma conta? <Link className="text-link" to="/login">Voltar para o login</Link></p></div></main></div>;
}
