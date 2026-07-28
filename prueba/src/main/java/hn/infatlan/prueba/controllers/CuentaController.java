package hn.infatlan.prueba.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hn.infatlan.prueba.dto.CuentaRequestDTO;
import hn.infatlan.prueba.dto.CuentaResponseDTO;
import hn.infatlan.prueba.dto.CuentaUpdateDTO;
import hn.infatlan.prueba.services.CuentaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuenta) {
        this.cuentaService = cuenta;
    }

    @GetMapping("/listar")
    public List<CuentaResponseDTO> listarCuentas() {
        return cuentaService.listarCuentas();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<CuentaResponseDTO> listarCuentasPorCliente(@PathVariable("idCliente") int idCliente) {
        return cuentaService.listarCuentasPorCliente(idCliente);
    }

    @GetMapping("/{id}")
    public CuentaResponseDTO obtenerCuenta(@PathVariable("id") int id) {
        return cuentaService.obtenerCuentaPorId(id);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponseDTO crearCuenta(@RequestBody CuentaRequestDTO datos) {
        return cuentaService.crearCuenta(datos);
    }

    @PutMapping("/{id}")
    public CuentaResponseDTO actualizarCuenta(@PathVariable("id") int id, @RequestBody CuentaUpdateDTO datos) {
        return cuentaService.actualizarCuenta(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable("id") int id) {
        cuentaService.eliminarCuenta(id);
    }

}
