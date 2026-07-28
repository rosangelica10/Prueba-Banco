package hn.infatlan.prueba.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.infatlan.prueba.dto.ClienteRequestDTO;
import hn.infatlan.prueba.dto.ClienteResponseDTO;
import hn.infatlan.prueba.dto.CuentaResumenDTO;
import hn.infatlan.prueba.entities.ClienteEntity;
import hn.infatlan.prueba.entities.CuentaEntity;
import hn.infatlan.prueba.exceptions.RecursoNoEncontradoException;
import hn.infatlan.prueba.repositories.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO datos) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(datos.nombre());
        cliente.setApellido(datos.apellido());
        cliente.setActivo(true);

        return toResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerClientePorId(int id) {
        return toResponseDTO(buscarClienteActivoOThrow(id));
    }

    @Transactional
    public ClienteResponseDTO actualizarCliente(int id, ClienteRequestDTO datos) {
        ClienteEntity cliente = buscarClienteOThrow(id);
        cliente.setNombre(datos.nombre());
        cliente.setApellido(datos.apellido());

        return toResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public void eliminarCliente(int id) {
        ClienteEntity cliente = buscarClienteOThrow(id);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    public ClienteEntity buscarClienteOThrow(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un cliente con id " + id));
    }

    private ClienteEntity buscarClienteActivoOThrow(int id) {
        ClienteEntity cliente = buscarClienteOThrow(id);
        if (!cliente.isActivo()) {
            throw new RecursoNoEncontradoException("El cliente con id " + id + " se encuentra inactivo");
        }
        return cliente;
    }

    private ClienteResponseDTO toResponseDTO(ClienteEntity cliente) {
        List<CuentaResumenDTO> cuentas = cliente.getCuentas() == null
                ? List.of()
                : cliente.getCuentas().stream().map(this::toCuentaResumenDTO).toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.isActivo(),
                cuentas
        );
    }

    private CuentaResumenDTO toCuentaResumenDTO(CuentaEntity cuenta) {
        return new CuentaResumenDTO(cuenta.getIdCuenta(), cuenta.getNumCuenta(), cuenta.getSaldo(), cuenta.isActivo());
    }

}
