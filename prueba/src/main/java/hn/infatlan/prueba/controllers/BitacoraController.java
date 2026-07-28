package hn.infatlan.prueba.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.infatlan.prueba.dto.BitacoraResponseDTO;
import hn.infatlan.prueba.services.BitacoraService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/bitacora")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping("/listar")
    public List<BitacoraResponseDTO> listarTodo() {
        return bitacoraService.listarTodo();
    }

    @GetMapping("/cuenta/{idCuenta}")
    public List<BitacoraResponseDTO> listarPorCuenta(@PathVariable("idCuenta") int idCuenta) {
        return bitacoraService.listarPorCuenta(idCuenta);
    }

}
