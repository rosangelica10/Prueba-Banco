import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css'
import App from './App.jsx'

import { ClientesPage } from "./modules/clientes";
import { CuentasPage } from "./modules/cuentas";
import { TransferenciasPage } from "./modules/transferencias";
import { MovimientosPage } from "./modules/movimientos";

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/clientes" element={<ClientesPage />} />
        <Route path="/cuentas" element={<CuentasPage />} />
        <Route path="/transferencias" element={<TransferenciasPage />} />
        <Route path="/movimientos" element={<MovimientosPage />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
)
