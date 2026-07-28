import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getTransferencias, createTransferencia } from "../services/TransferenciaService";
import { getCuentas } from "../../cuentas/services/CuentaService";
import TransferenciaForm from "../components/TransferenciaForm";
import TransferenciaList from "../components/TransferenciaList";

function TransferenciasPage() {
  const [cuentas, setCuentas] = useState([]);
  const [transferencias, setTransferencias] = useState([]);
  const [error, setError] = useState(null);
  const [ultimoResultado, setUltimoResultado] = useState(null);

  useEffect(() => {
    cargarCuentas();
    cargarTransferencias();
  }, []);

  const cargarCuentas = () => {
    getCuentas()
      .then((res) => setCuentas(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const cargarTransferencias = () => {
    getTransferencias()
      .then((res) => setTransferencias(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const obtenerMensajeError = (err) =>
    err.response?.data?.message || "Ocurrió un error al comunicarse con el servidor";

  const realizarTransferencia = (datos) => {
    setError(null);
    setUltimoResultado(null);
    createTransferencia(datos)
      .then((res) => {
        setUltimoResultado(res.data);
        cargarCuentas();
        cargarTransferencias();
      })
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  return (
    <div>
      <p><Link to="/">Inicio</Link></p>
      <h1>Transferencias entre Cuentas</h1>
      {error && <div className="error-banner">{error}</div>}
      {ultimoResultado && (
        <div className="success-banner">
          Transferencia realizada. Saldo cuenta origen: {Number(ultimoResultado.saldoCuentaOrigen).toFixed(2)} —
          Saldo cuenta destino: {Number(ultimoResultado.saldoCuentaDestino).toFixed(2)}
        </div>
      )}
      <TransferenciaForm onSubmit={realizarTransferencia} cuentas={cuentas} />
      <TransferenciaList transferencias={transferencias} />
    </div>
  );
}

export default TransferenciasPage;
