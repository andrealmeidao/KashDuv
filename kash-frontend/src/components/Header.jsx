export default function Header() {
  return (
    <header style={{
      backgroundColor: 'var(--bg-secondary)',
      padding: '20px 40px',
      borderBottom: '2px solid var(--accent)',
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center'
    }}>
      <div>
        <h1 style={{ color: 'var(--accent)', fontSize: '32px', fontWeight: 'bold' }}>
          KASH
        </h1>
        <p style={{ color: 'var(--text-secondary)', fontSize: '12px' }}>
          Controle financeiro inteligente
        </p>
      </div>
      <nav style={{ display: 'flex', gap: '20px' }}>
        <a href="/dashboard" style={{ color: 'var(--text-primary)', textDecoration: 'none', cursor: 'pointer' }}>
          Dashboard
        </a>
        <a href="/despesas" style={{ color: 'var(--text-primary)', textDecoration: 'none', cursor: 'pointer' }}>
          Despesas
        </a>
        <a href="/receitas" style={{ color: 'var(--text-primary)', textDecoration: 'none', cursor: 'pointer' }}>
          Receitas
        </a>
      </nav>
    </header>
  );
}