import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getClientes, createCliente, updateCliente, deleteCliente } from "../services/ClienteService";
import ClienteForm from "../components/ClienteForm";
import ClienteList from "../components/ClienteList";

function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [clienteEnEdicion, setClienteEnEdicion] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    cargarClientes();
  }, []);

  const cargarClientes = () => {
    getClientes()
      .then((res) => setClientes(res.data))
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const obtenerMensajeError = (err) =>
    err.response?.data?.message || "Ocurrió un error al comunicarse con el servidor";

  const guardarCliente = (cliente) => {
    setError(null);
    const operacion = clienteEnEdicion
      ? updateCliente(clienteEnEdicion.id, cliente)
      : createCliente(cliente);

    operacion
      .then(() => {
        setClienteEnEdicion(null);
        cargarClientes();
      })
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  const desactivarCliente = (id) => {
    setError(null);
    deleteCliente(id)
      .then(() => cargarClientes())
      .catch((err) => setError(obtenerMensajeError(err)));
  };

  return (
    <div>
      <p><Link to="/">Inicio</Link></p>
      <h1>Gestión de Clientes</h1>
      {error && <div className="error-banner">{error}</div>}
      <ClienteForm
        onSubmit={guardarCliente}
        initialData={clienteEnEdicion}
        onCancel={() => setClienteEnEdicion(null)}
      />
      <ClienteList
        clientes={clientes}
        onEdit={setClienteEnEdicion}
        onDeactivate={desactivarCliente}
      />
    </div>
  );
}

export default ClientesPage;
