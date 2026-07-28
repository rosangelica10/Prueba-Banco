import axios from "axios";

const API_URL = "http://localhost:8080/cuentas";

export const getCuentas = () => axios.get(`${API_URL}/listar`);
export const getCuentasPorCliente = (idCliente) => axios.get(`${API_URL}/cliente/${idCliente}`);
export const getCuentaById = (id) => axios.get(`${API_URL}/${id}`);
export const createCuenta = (cuenta) => axios.post(`${API_URL}/crear`, cuenta);
export const updateCuenta = (id, cuenta) => axios.put(`${API_URL}/${id}`, cuenta);
export const deleteCuenta = (id) => axios.delete(`${API_URL}/${id}`);
