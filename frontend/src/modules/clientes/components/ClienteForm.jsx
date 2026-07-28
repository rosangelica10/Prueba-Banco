import { useEffect, useState } from "react";

function ClienteForm({ onSubmit, initialData, onCancel }) {
  const [nombre, setNombre] = useState(initialData?.nombre || "");
  const [apellido, setApellido] = useState(initialData?.apellido || "");

  useEffect(() => {
    setNombre(initialData?.nombre || "");
    setApellido(initialData?.apellido || "");
  }, [initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ nombre, apellido });
    if (!initialData) {
      setNombre("");
      setApellido("");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Nombre"
        value={nombre}
        onChange={(e) => setNombre(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Apellido"
        value={apellido}
        onChange={(e) => setApellido(e.target.value)}
        required
      />
      <button type="submit">{initialData ? "Guardar cambios" : "Guardar"}</button>
      {initialData && (
        <button type="button" onClick={onCancel}>
          Cancelar
        </button>
      )}
    </form>
  );
}

export default ClienteForm;
