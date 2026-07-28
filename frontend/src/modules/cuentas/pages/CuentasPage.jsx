import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getCuentas, createCuenta, updateCuenta, deleteCuenta } from "../services/CuentaService";
import { getClientes } from "../../clientes/services/ClienteService";
import CuentaForm from "../components/CuentaForm";
import CuentaList from "../components/CuentaList";

function CuentasPage() {
  const [cuentas, setCuentas] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [cuentaEnEdicion, setCuentaEnEdicion] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    cargarCuentas();
    getClientes()
      .then((res) => setClientes(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  }, []);

  const cargarCuentas = () => {
    getCuentas()
      .then((res) => setCuentas(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const obtenerMensajeError = (err) =>
    err.response?.data?.message || "Ocurrió un error al comunicarse con el servidor";

  const guardarCuenta = (cuenta) => {
    setError(null);
    const operacion = cuentaEnEdicion
      ? updateCuenta(cuentaEnEdicion.idCuenta, cuenta)
      : createCuenta(cuenta);

    operacion
      .then(() => {
        setCuentaEnEdicion(null);
        cargarCuentas();
      })
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const desactivarCuenta = (id) => {
    setError(null);
    deleteCuenta(id)
      .then(() => cargarCuentas())
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  return (
    <div>
      <p><Link to="/">Inicio</Link></p>
      <h1>Gestión de Cuentas</h1>
      {error && <div className="error-banner">{error}</div>}
      <CuentaForm
        onSubmit={guardarCuenta}
        initialData={cuentaEnEdicion}
        onCancel={() => setCuentaEnEdicion(null)}
        clientes={clientes}
      />
      <CuentaList
        cuentas={cuentas}
        onEdit={setCuentaEnEdicion}
        onDeactivate={desactivarCuenta}
      />
    </div>
  );
}

export default CuentasPage;
