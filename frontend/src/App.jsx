import { Link } from "react-router-dom";
import './App.css'

function App() {  

  return (
     <div>
      <h1>Bienvenido a la aplicación</h1>
      <nav>
        <ul>
          <li><Link to="/clientes">Gestión de Clientes</Link></li>
          <li><Link to="/cuentas">Gestión de Cuentas</Link></li>
          <li><Link to="/transferencias">Transferencias</Link></li>
          <li><Link to="/movimientos">Débito / Crédito</Link></li>
        </ul>
      </nav>
    </div>
  );
}
export default App;
