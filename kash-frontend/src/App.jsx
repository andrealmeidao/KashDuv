import { useState, useEffect } from 'react';
import axios from 'axios';
import Header from './components/Header';
import './index.css';

const API_BASE = 'http://localhost:8081/api';

export default function App() {
  const [despesas, setDespesas] = useState([]);
  const [receitas, setReceitas] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    setLoading(true);
    try {
      const [despRes, recRes] = await Promise.all([
        axios.get(`${API_BASE}/despesas`),
        axios.get(`${API_BASE}/receitas`)
      ]);
      setDespesas(despRes.data);
      setReceitas(recRes.data);
    } catch (err) {
      console.error('Erro ao carregar dados:', err);
    } finally {
      setLoading(false);
    }
  };

  const totalDespesas = despesas.reduce((sum, d) => sum + (d.valor || 0), 0);
  const totalReceitas = receitas.reduce((sum, r) => sum + (r.valor || 0), 0);
  const saldo = totalReceitas - totalDespesas;

  return (
    <div style={{ minHeight: '100vh', backgroundColor: 'var(--bg-primary)' }}>
      <Header />
      
      <main style={{ padding: '40px' }}>
        <h2 style={{ color: 'var(--accent)', marginBottom: '30px', fontSize: '28px' }}>
          Dashboard
        </h2>

        {/* Cards de resumo */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px', marginBottom: '40px' }}>
          <Card title="Total de Receitas" valor={totalReceitas} cor="var(--accent-light)" />
          <Card title="Total de Despesas" valor={totalDespesas} cor="var(--accent)" />
          <Card title="Saldo" valor={saldo} cor={saldo >= 0 ? '#4caf50' : 'var(--accent)'} />
        </div>

        {/* Lista de despesas */}
        <div style={{ backgroundColor: 'var(--bg-secondary)', padding: '20px', borderRadius: '8px', borderLeft: '4px solid var(--accent)' }}>
          <h3 style={{ color: 'var(--accent)', marginBottom: '15px' }}>Despesas Recentes</h3>
          {loading ? (
            <p>Carregando...</p>
          ) : despesas.length === 0 ? (
            <p style={{ color: 'var(--text-secondary)' }}>Nenhuma despesa registrada</p>
          ) : (
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr style={{ borderBottom: '1px solid var(--border)' }}>
                  <th style={{ textAlign: 'left', padding: '10px', color: 'var(--accent)' }}>Descrição</th>
                  <th style={{ textAlign: 'left', padding: '10px', color: 'var(--accent)' }}>Categoria</th>
                  <th style={{ textAlign: 'right', padding: '10px', color: 'var(--accent)' }}>Valor</th>
                  <th style={{ textAlign: 'left', padding: '10px', color: 'var(--accent)' }}>Data</th>
                </tr>
              </thead>
              <tbody>
                {despesas.map(d => (
                  <tr key={d.id} style={{ borderBottom: '1px solid var(--border)' }}>
                    <td style={{ padding: '10px' }}>{d.descricao}</td>
                    <td style={{ padding: '10px' }}>{d.categoria}</td>
                    <td style={{ padding: '10px', textAlign: 'right', color: 'var(--accent)' }}>
                      R$ {d.valor?.toFixed(2)}
                    </td>
                    <td style={{ padding: '10px' }}>{d.data}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </main>
    </div>
  );
}

function Card({ title, valor, cor }) {
  return (
    <div style={{
      backgroundColor: 'var(--bg-secondary)',
      padding: '20px',
      borderRadius: '8px',
      borderTop: `4px solid ${cor}`
    }}>
      <p style={{ color: 'var(--text-secondary)', fontSize: '14px', marginBottom: '10px' }}>
        {title}
      </p>
      <h3 style={{ color: cor, fontSize: '28px', fontWeight: 'bold' }}>
        R$ {valor.toFixed(2)}
      </h3>
    </div>
  );
}