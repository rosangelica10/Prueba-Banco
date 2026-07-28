package hn.infatlan.prueba.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.infatlan.prueba.dto.CuentaResponseDTO;
import hn.infatlan.prueba.dto.MovimientoRequestDTO;
import hn.infatlan.prueba.services.MovimientoService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping("/debito")
    public CuentaResponseDTO debitar(@RequestBody MovimientoRequestDTO datos) {
        return movimientoService.debitar(datos);
    }

    @PostMapping("/credito")
    public CuentaResponseDTO acreditar(@RequestBody MovimientoRequestDTO datos) {
        return movimientoService.acreditar(datos);
    }

}
