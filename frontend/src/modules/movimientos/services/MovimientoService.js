import axios from "axios";

const API_URL = "http://localhost:8080/movimientos";

export const debitarCuenta = (movimiento) => axios.post(`${API_URL}/debito`, movimiento);
export const acreditarCuenta = (movimiento) => axios.post(`${API_URL}/credito`, movimiento);
