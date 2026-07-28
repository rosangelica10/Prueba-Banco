import { useState } from "react";

function TransferenciaForm({ onSubmit, cuentas }) {
  const [idCuentaOrigen, setIdCuentaOrigen] = useState("");
  const [idCuentaDestino, setIdCuentaDestino] = useState("");
  const [monto, setMonto] = useState("");
  const [usuario, setUsuario] = useState("");
  const [descripcion, setDescripcion] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      idCuentaOrigen: Number(idCuentaOrigen),
      idCuentaDestino: Number(idCuentaDestino),
      monto: Number(monto),
      usuario,
      descripcion,
    });
    setMonto("");
    setDescripcion("");
  };

  const cuentasActivas = cuentas.filter((c) => c.activo);

  return (
    <form onSubmit={handleSubmit}>
      <select value={idCuentaOrigen} onChange={(e) => setIdCuentaOrigen(e.target.value)} required>
        <option value="">Cuenta origen</option>
        {cuentasActivas.map((c) => (
          <option key={c.idCuenta} value={c.idCuenta}>
            {c.numCuenta} (saldo: {Number(c.saldo).toFixed(2)})
          </option>
        ))}
      </select>
      <select value={idCuentaDestino} onChange={(e) => setIdCuentaDestino(e.target.value)} required>
        <option value="">Cuenta destino</option>
        {cuentasActivas.map((c) => (
          <option key={c.idCuenta} value={c.idCuenta}>
            {c.numCuenta} (saldo: {Number(c.saldo).toFixed(2)})
          </option>
        ))}
      </select>
      <input
        type="number"
        step="0.01"
        min="0.01"
        placeholder="Monto"
        value={monto}
        onChange={(e) => setMonto(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Usuario"
        value={usuario}
        onChange={(e) => setUsuario(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Descripción (opcional)"
        value={descripcion}
        onChange={(e) => setDescripcion(e.target.value)}
      />
      <button type="submit">Transferir</button>
    </form>
  );
}

export default TransferenciaForm;
