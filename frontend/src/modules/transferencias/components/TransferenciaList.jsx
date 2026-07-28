function TransferenciaList({ transferencias }) {
  return (
    <table>
      <thead>
        <tr>
          <th>Fecha</th>
          <th>Origen</th>
          <th>Destino</th>
          <th>Monto</th>
          <th>Usuario</th>
          <th>Descripción</th>
        </tr>
      </thead>
      <tbody>
        {transferencias.map((t) => (
          <tr key={t.idTransferencia}>
            <td>{new Date(t.fecha).toLocaleString()}</td>
            <td>{t.numCuentaOrigen}</td>
            <td>{t.numCuentaDestino}</td>
            <td>{Number(t.monto).toFixed(2)}</td>
            <td>{t.usuario}</td>
            <td>{t.descripcion}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default TransferenciaList;
