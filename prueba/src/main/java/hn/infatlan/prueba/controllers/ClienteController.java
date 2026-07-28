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

import hn.infatlan.prueba.dto.ClienteRequestDTO;
import hn.infatlan.prueba.dto.ClienteResponseDTO;
import hn.infatlan.prueba.services.ClienteService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService cliente) {
        this.clienteService = cliente;
    }

    @GetMapping("/listar")
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerCliente(@PathVariable("id") int id) {
        return clienteService.obtenerClientePorId(id);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO crearCliente(@RequestBody ClienteRequestDTO datos) {
        return clienteService.crearCliente(datos);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO actualizarCliente(@PathVariable("id") int id, @RequestBody ClienteRequestDTO datos) {
        return clienteService.actualizarCliente(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable("id") int id) {
        clienteService.eliminarCliente(id);
    }

}
