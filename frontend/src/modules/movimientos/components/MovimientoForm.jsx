import { useState } from "react";

function MovimientoForm({ onSubmit, cuentas }) {
  const [idCuenta, setIdCuenta] = useState("");
  const [tipo, setTipo] = useState("credito");
  const [monto, setMonto] = useState("");
  const [usuario, setUsuario] = useState("");
  const [descripcion, setDescripcion] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(tipo, { idCuenta: Number(idCuenta), monto: Number(monto), usuario, descripcion });
    setMonto("");
    setDescripcion("");
  };

  const cuentasActivas = cuentas.filter((c) => c.activo);

  return (
    <form onSubmit={handleSubmit}>
      <select value={idCuenta} onChange={(e) => setIdCuenta(e.target.value)} required>
        <option value="">Seleccione una cuenta</option>
        {cuentasActivas.map((c) => (
          <option key={c.idCuenta} value={c.idCuenta}>
            {c.numCuenta} (saldo: {Number(c.saldo).toFixed(2)})
          </option>
        ))}
      </select>
      <select value={tipo} onChange={(e) => setTipo(e.target.value)}>
        <option value="credito">Crédito (depósito)</option>
        <option value="debito">Débito (retiro/ajuste)</option>
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
        placeholder="Descripción (motivo del ajuste)"
        value={descripcion}
        onChange={(e) => setDescripcion(e.target.value)}
      />
      <button type="submit">Aplicar movimiento</button>
    </form>
  );
}

export default MovimientoForm;
