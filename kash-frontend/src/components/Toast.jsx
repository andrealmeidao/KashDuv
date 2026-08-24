import { CheckCircle2, X, XCircle } from 'lucide-react';

export default function Toast({ toast, onClose }) {
  if (!toast) return null;
  const success = toast.type === 'success';
  return <div className={`toast toast-${toast.type}`} role="status" aria-live="polite"><span className="toast-icon">{success ? <CheckCircle2 size={19} /> : <XCircle size={19} />}</span><span>{toast.message}</span><button className="icon-button" onClick={onClose} aria-label="Fechar aviso"><X size={17} /></button></div>;
}
