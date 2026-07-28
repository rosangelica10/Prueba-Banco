import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { debitarCuenta, acreditarCuenta } from "../services/MovimientoService";
import { getCuentas } from "../../cuentas/services/CuentaService";
import MovimientoForm from "../components/MovimientoForm";

function MovimientosPage() {
  const [cuentas, setCuentas] = useState([]);
  const [error, setError] = useState(null);
  const [ultimoResultado, setUltimoResultado] = useState(null);

  useEffect(() => {
    cargarCuentas();
  }, []);

  const cargarCuentas = () => {
    getCuentas()
      .then((res) => setCuentas(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const obtenerMensajeError = (err) =>
    err.response?.data?.message || "Ocurrió un error al comunicarse con el servidor";

  const aplicarMovimiento = (tipo, datos) => {
    setError(null);
    setUltimoResultado(null);
    const operacion = tipo === "debito" ? debitarCuenta(datos) : acreditarCuenta(datos);

    operacion
      .then((res) => {
        setUltimoResultado(res.data);
        cargarCuentas();
      })
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  return (
    <div>
      <p><Link to="/">Inicio</Link></p>
      <h1>Débito / Crédito Manual</h1>
      {error && <div className="error-banner">{error}</div>}
      {ultimoResultado && (
        <div className="success-banner">
          Cuenta {ultimoResultado.numCuenta}: nuevo saldo {Number(ultimoResultado.saldo).toFixed(2)}
        </div>
      )}
      <MovimientoForm onSubmit={aplicarMovimiento} cuentas={cuentas} />
    </div>
  );
}

export default MovimientosPage;
