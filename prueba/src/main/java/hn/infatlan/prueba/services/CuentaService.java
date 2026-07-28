package hn.infatlan.prueba.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.infatlan.prueba.dto.ClienteResumenDTO;
import hn.infatlan.prueba.dto.CuentaRequestDTO;
import hn.infatlan.prueba.dto.CuentaResponseDTO;
import hn.infatlan.prueba.dto.CuentaUpdateDTO;
import hn.infatlan.prueba.entities.ClienteEntity;
import hn.infatlan.prueba.entities.CuentaEntity;
import hn.infatlan.prueba.exceptions.OperacionInvalidaException;
import hn.infatlan.prueba.exceptions.RecursoNoEncontradoException;
import hn.infatlan.prueba.repositories.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;

    CuentaService(CuentaRepository cuentaRepository, ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO datos) {
        if (datos.numCuenta() == null || datos.numCuenta().isBlank()) {
            throw new OperacionInvalidaException("El número de cuenta es obligatorio");
        }
        if (cuentaRepository.existsByNumCuenta(datos.numCuenta())) {
            throw new OperacionInvalidaException("Ya existe una cuenta con el número " + datos.numCuenta());
        }

        BigDecimal saldoInicial = datos.saldoInicial() == null ? BigDecimal.ZERO : datos.saldoInicial();
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperacionInvalidaException("El saldo inicial no puede ser negativo");
        }

        ClienteEntity cliente = clienteService.buscarClienteOThrow(datos.idCliente());
        if (!cliente.isActivo()) {
            throw new OperacionInvalidaException("No se puede asignar una cuenta a un cliente inactivo");
        }

        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setNumCuenta(datos.numCuenta());
        cuenta.setSaldo(saldoInicial);
        cuenta.setActivo(true);
        cuenta.setCliente(cliente);

        return toResponseDTO(cuentaRepository.save(cuenta));
    }

    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> listarCuentas() {
        return cuentaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> listarCuentasPorCliente(int idCliente) {
        clienteService.buscarClienteOThrow(idCliente);
        return cuentaRepository.findByCliente_Id(idCliente).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CuentaResponseDTO obtenerCuentaPorId(int id) {
        return toResponseDTO(buscarCuentaOThrow(id));
    }

    @Transactional
    public CuentaResponseDTO actualizarCuenta(int id, CuentaUpdateDTO datos) {
        CuentaEntity cuenta = buscarCuentaOThrow(id);

        if (datos.numCuenta() != null && !datos.numCuenta().isBlank()
                && !datos.numCuenta().equals(cuenta.getNumCuenta())) {
            if (cuentaRepository.existsByNumCuenta(datos.numCuenta())) {
                throw new OperacionInvalidaException("Ya existe una cuenta con el número " + datos.numCuenta());
            }
            cuenta.setNumCuenta(datos.numCuenta());
        }

        if (datos.idCliente() != null) {
            ClienteEntity nuevoCliente = clienteService.buscarClienteOThrow(datos.idCliente());
            if (!nuevoCliente.isActivo()) {
                throw new OperacionInvalidaException("No se puede asignar una cuenta a un cliente inactivo");
            }
            cuenta.setCliente(nuevoCliente);
        }

        return toResponseDTO(cuentaRepository.save(cuenta));
    }

    @Transactional
    public void eliminarCuenta(int id) {
        CuentaEntity cuenta = buscarCuentaOThrow(id);
        cuenta.setActivo(false);
        cuentaRepository.save(cuenta);
    }

    public CuentaEntity buscarCuentaOThrow(int id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una cuenta con id " + id));
    }

    public CuentaEntity buscarCuentaActivaOThrow(int id) {
        CuentaEntity cuenta = buscarCuentaOThrow(id);
        if (!cuenta.isActivo()) {
            throw new OperacionInvalidaException("La cuenta con id " + id + " se encuentra inactiva");
        }
        return cuenta;
    }

    public CuentaEntity guardar(CuentaEntity cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public CuentaResponseDTO toResponseDTO(CuentaEntity cuenta) {
        ClienteEntity cliente = cuenta.getCliente();
        ClienteResumenDTO clienteResumen = cliente == null
                ? null
                : new ClienteResumenDTO(cliente.getId(), cliente.getNombre(), cliente.getApellido());

        return new CuentaResponseDTO(
                cuenta.getIdCuenta(),
                cuenta.getNumCuenta(),
                cuenta.getSaldo(),
                cuenta.isActivo(),
                clienteResumen
        );
    }

}
