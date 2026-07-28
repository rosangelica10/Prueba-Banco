function ClienteList({ clientes, onEdit, onDeactivate }) {
  return (
    <table>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Apellido</th>
          <th>Cuentas</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {clientes.map((c) => (
          <tr key={c.id}>
            <td>{c.nombre}</td>
            <td>{c.apellido}</td>
            <td>{c.cuentas?.length || 0}</td>
            <td>{c.activo ? "Activo" : "Inactivo"}</td>
            <td>
              <button onClick={() => onEdit(c)}>Editar</button>
              {c.activo && (
                <button onClick={() => onDeactivate(c.id)}>Desactivar</button>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ClienteList;
