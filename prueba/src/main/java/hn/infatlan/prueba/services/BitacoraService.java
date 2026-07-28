package hn.infatlan.prueba.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.infatlan.prueba.dto.BitacoraResponseDTO;
import hn.infatlan.prueba.entities.BitacoraEntity;
import hn.infatlan.prueba.entities.CuentaEntity;
import hn.infatlan.prueba.entities.TipoOperacion;
import hn.infatlan.prueba.entities.TransferenciaEntity;
import hn.infatlan.prueba.repositories.BitacoraRepository;

@Service
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    BitacoraService(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

    @Transactional
    public void registrar(CuentaEntity cuenta, TipoOperacion tipo, BigDecimal monto, BigDecimal saldoAnterior,
            BigDecimal saldoNuevo, String usuario, String descripcion, TransferenciaEntity transferencia) {

        BitacoraEntity registro = new BitacoraEntity();
        registro.setCuenta(cuenta);
        registro.setTipoOperacion(tipo);
        registro.setMonto(monto);
        registro.setSaldoAnterior(saldoAnterior);
        registro.setSaldoNuevo(saldoNuevo);
        registro.setFecha(LocalDateTime.now());
        registro.setUsuario(usuario);
        registro.setDescripcion(descripcion);
        registro.setTransferencia(transferencia);

        bitacoraRepository.save(registro);
    }

    @Transactional(readOnly = true)
    public List<BitacoraResponseDTO> listarTodo() {
        return bitacoraRepository.findAllByOrderByFechaDesc().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BitacoraResponseDTO> listarPorCuenta(int idCuenta) {
        return bitacoraRepository.findByCuenta_IdCuentaOrderByFechaDesc(idCuenta).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private BitacoraResponseDTO toResponseDTO(BitacoraEntity registro) {
        return new BitacoraResponseDTO(
                registro.getId(),
                registro.getCuenta().getIdCuenta(),
                registro.getCuenta().getNumCuenta(),
                registro.getTipoOperacion().name(),
                registro.getMonto(),
                registro.getSaldoAnterior(),
                registro.getSaldoNuevo(),
                registro.getFecha(),
                registro.getUsuario(),
                registro.getDescripcion()
        );
    }

}
