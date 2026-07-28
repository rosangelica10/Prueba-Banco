import { useEffect, useState } from "react";

function CuentaForm({ onSubmit, initialData, onCancel, clientes }) {
  const [numCuenta, setNumCuenta] = useState(initialData?.numCuenta || "");
  const [idCliente, setIdCliente] = useState(initialData?.cliente?.id || "");
  const [saldoInicial, setSaldoInicial] = useState("0");

  useEffect(() => {
    setNumCuenta(initialData?.numCuenta || "");
    setIdCliente(initialData?.cliente?.id || "");
    setSaldoInicial("0");
  }, [initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (initialData) {
      onSubmit({ numCuenta, idCliente: Number(idCliente) });
    } else {
      onSubmit({ numCuenta, idCliente: Number(idCliente), saldoInicial: Number(saldoInicial) });
      setNumCuenta("");
      setIdCliente("");
      setSaldoInicial("0");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Número de cuenta"
        value={numCuenta}
        onChange={(e) => setNumCuenta(e.target.value)}
        required
      />
      <select value={idCliente} onChange={(e) => setIdCliente(e.target.value)} required>
        <option value="">Seleccione un cliente</option>
        {clientes.filter((c) => c.activo).map((c) => (
          <option key={c.id} value={c.id}>
            {c.nombre} {c.apellido}
          </option>
        ))}
      </select>
      {!initialData && (
        <input
          type="number"
          step="0.01"
          min="0"
          placeholder="Saldo inicial"
          value={saldoInicial}
          onChange={(e) => setSaldoInicial(e.target.value)}
        />
      )}
      <button type="submit">{initialData ? "Guardar cambios" : "Crear cuenta"}</button>
      {initialData && (
        <button type="button" onClick={onCancel}>
          Cancelar
        </button>
      )}
    </form>
  );
}

export default CuentaForm;
