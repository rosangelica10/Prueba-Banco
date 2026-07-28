function CuentaList({ cuentas, onEdit, onDeactivate }) {
  return (
    <table>
      <thead>
        <tr>
          <th>Cuenta</th>
          <th>Cliente</th>
          <th>Saldo</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {cuentas.map((c) => (
          <tr key={c.idCuenta}>
            <td>{c.numCuenta}</td>
            <td>{c.cliente ? `${c.cliente.nombre} ${c.cliente.apellido}` : "-"}</td>
            <td>{Number(c.saldo).toFixed(2)}</td>
            <td>{c.activo ? "Activa" : "Inactiva"}</td>
            <td>
              <button onClick={() => onEdit(c)}>Editar</button>
              {c.activo && (
                <button onClick={() => onDeactivate(c.idCuenta)}>Desactivar</button>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default CuentaList;
