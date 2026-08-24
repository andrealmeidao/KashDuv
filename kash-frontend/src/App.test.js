import { render, screen } from '@testing-library/react';
import App from './App';

test('renders login screen', () => {
  render(<App />);
  expect(screen.getByText('KASH')).toBeInTheDocument();
  expect(screen.getByRole('button', { name: 'Entrar na conta' })).toBeInTheDocument();
  expect(screen.getByLabelText('Usuário')).toBeRequired();
  expect(screen.getByLabelText('Senha')).toBeRequired();
});
