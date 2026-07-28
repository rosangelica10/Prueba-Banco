package hn.infatlan.prueba.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.infatlan.prueba.dto.TransferenciaRequestDTO;
import hn.infatlan.prueba.dto.TransferenciaResponseDTO;
import hn.infatlan.prueba.entities.CuentaEntity;
import hn.infatlan.prueba.entities.TipoOperacion;
import hn.infatlan.prueba.entities.TransferenciaEntity;
import hn.infatlan.prueba.exceptions.OperacionInvalidaException;
import hn.infatlan.prueba.exceptions.SaldoInsuficienteException;
import hn.infatlan.prueba.repositories.TransferenciaRepository;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final CuentaService cuentaService;
    private final BitacoraService bitacoraService;

    TransferenciaService(TransferenciaRepository transferenciaRepository, CuentaService cuentaService,
            BitacoraService bitacoraService) {
        this.transferenciaRepository = transferenciaRepository;
        this.cuentaService = cuentaService;
        this.bitacoraService = bitacoraService;
    }

    @Transactional
    public TransferenciaResponseDTO transferir(TransferenciaRequestDTO datos) {
        if (datos.monto() == null || datos.monto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacionInvalidaException("El monto de la transferencia debe ser mayor a cero");
        }
        if (datos.idCuentaOrigen() == datos.idCuentaDestino()) {
            throw new OperacionInvalidaException("La cuenta de origen y destino no pueden ser la misma");
        }

        CuentaEntity cuentaOrigen = cuentaService.buscarCuentaActivaOThrow(datos.idCuentaOrigen());
        CuentaEntity cuentaDestino = cuentaService.buscarCuentaActivaOThrow(datos.idCuentaDestino());

        BigDecimal saldoAnteriorOrigen = cuentaOrigen.getSaldo();
        if (saldoAnteriorOrigen.compareTo(datos.monto()) < 0) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente en la cuenta " + cuentaOrigen.getNumCuenta() + " para realizar la transferencia");
        }
        BigDecimal saldoNuevoOrigen = saldoAnteriorOrigen.subtract(datos.monto());

        BigDecimal saldoAnteriorDestino = cuentaDestino.getSaldo();
        BigDecimal saldoNuevoDestino = saldoAnteriorDestino.add(datos.monto());

        cuentaOrigen.setSaldo(saldoNuevoOrigen);
        cuentaDestino.setSaldo(saldoNuevoDestino);
        cuentaService.guardar(cuentaOrigen);
        cuentaService.guardar(cuentaDestino);

        TransferenciaEntity transferencia = new TransferenciaEntity();
        transferencia.setCuentaOrigen(cuentaOrigen);
        transferencia.setCuentaDestino(cuentaDestino);
        transferencia.setMonto(datos.monto());
        transferencia.setFecha(LocalDateTime.now());
        transferencia.setUsuario(datos.usuario());
        transferencia.setDescripcion(datos.descripcion());
        transferencia = transferenciaRepository.save(transferencia);

        bitacoraService.registrar(cuentaOrigen, TipoOperacion.TRANSFERENCIA_ENVIADA, datos.monto(),
                saldoAnteriorOrigen, saldoNuevoOrigen, datos.usuario(), datos.descripcion(), transferencia);
        bitacoraService.registrar(cuentaDestino, TipoOperacion.TRANSFERENCIA_RECIBIDA, datos.monto(),
                saldoAnteriorDestino, saldoNuevoDestino, datos.usuario(), datos.descripcion(), transferencia);

        return toResponseDTO(transferencia, saldoNuevoOrigen, saldoNuevoDestino);
    }

    @Transactional(readOnly = true)
    public List<TransferenciaResponseDTO> listarTransferencias() {
        return transferenciaRepository.findAll().stream()
                .map(t -> toResponseDTO(t, t.getCuentaOrigen().getSaldo(), t.getCuentaDestino().getSaldo()))
                .toList();
    }

    private TransferenciaResponseDTO toResponseDTO(TransferenciaEntity transferencia, BigDecimal saldoOrigen,
            BigDecimal saldoDestino) {
        return new TransferenciaResponseDTO(
                transferencia.getIdTransferencia(),
                transferencia.getCuentaOrigen().getIdCuenta(),
                transferencia.getCuentaOrigen().getNumCuenta(),
                transferencia.getCuentaDestino().getIdCuenta(),
                transferencia.getCuentaDestino().getNumCuenta(),
                transferencia.getMonto(),
                transferencia.getFecha(),
                transferencia.getUsuario(),
                transferencia.getDescripcion(),
                saldoOrigen,
                saldoDestino
        );
    }

}
