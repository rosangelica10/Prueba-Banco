package hn.infatlan.prueba.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hn.infatlan.prueba.dto.TransferenciaRequestDTO;
import hn.infatlan.prueba.dto.TransferenciaResponseDTO;
import hn.infatlan.prueba.services.TransferenciaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @GetMapping("/listar")
    public List<TransferenciaResponseDTO> listarTransferencias() {
        return transferenciaService.listarTransferencias();
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferenciaResponseDTO crearTransferencia(@RequestBody TransferenciaRequestDTO datos) {
        return transferenciaService.transferir(datos);
    }

}
