import axios from "axios";

const API_URL = "http://localhost:8080/transferencias";

export const getTransferencias = () => axios.get(`${API_URL}/listar`);
export const createTransferencia = (transferencia) => axios.post(`${API_URL}/crear`, transferencia);
