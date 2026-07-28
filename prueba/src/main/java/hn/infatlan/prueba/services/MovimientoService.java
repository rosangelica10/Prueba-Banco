package hn.infatlan.prueba.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.infatlan.prueba.dto.CuentaResponseDTO;
import hn.infatlan.prueba.dto.MovimientoRequestDTO;
import hn.infatlan.prueba.entities.CuentaEntity;
import hn.infatlan.prueba.entities.TipoOperacion;
import hn.infatlan.prueba.exceptions.OperacionInvalidaException;
import hn.infatlan.prueba.exceptions.SaldoInsuficienteException;

@Service
public class MovimientoService {

    private final CuentaService cuentaService;
    private final BitacoraService bitacoraService;

    MovimientoService(CuentaService cuentaService, BitacoraService bitacoraService) {
        this.cuentaService = cuentaService;
        this.bitacoraService = bitacoraService;
    }

    @Transactional
    public CuentaResponseDTO debitar(MovimientoRequestDTO datos) {
        validarMonto(datos.monto());
        CuentaEntity cuenta = cuentaService.buscarCuentaActivaOThrow(datos.idCuenta());

        BigDecimal saldoAnterior = cuenta.getSaldo();
        if (saldoAnterior.compareTo(datos.monto()) < 0) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente en la cuenta " + cuenta.getNumCuenta() + " para realizar el débito");
        }
        BigDecimal saldoNuevo = saldoAnterior.subtract(datos.monto());
        cuenta.setSaldo(saldoNuevo);
        cuentaService.guardar(cuenta);

        bitacoraService.registrar(cuenta, TipoOperacion.DEBITO, datos.monto(), saldoAnterior, saldoNuevo,
                datos.usuario(), datos.descripcion(), null);

        return cuentaService.toResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO acreditar(MovimientoRequestDTO datos) {
        validarMonto(datos.monto());
        CuentaEntity cuenta = cuentaService.buscarCuentaActivaOThrow(datos.idCuenta());

        BigDecimal saldoAnterior = cuenta.getSaldo();
        BigDecimal saldoNuevo = saldoAnterior.add(datos.monto());
        cuenta.setSaldo(saldoNuevo);
        cuentaService.guardar(cuenta);

        bitacoraService.registrar(cuenta, TipoOperacion.CREDITO, datos.monto(), saldoAnterior, saldoNuevo,
                datos.usuario(), datos.descripcion(), null);

        return cuentaService.toResponseDTO(cuenta);
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacionInvalidaException("El monto debe ser mayor a cero");
        }
    }

}
