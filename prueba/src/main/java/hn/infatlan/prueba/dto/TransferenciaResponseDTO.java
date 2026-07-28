package hn.infatlan.prueba.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaResponseDTO(
        int idTransferencia,
        int idCuentaOrigen,
        String numCuentaOrigen,
        int idCuentaDestino,
        String numCuentaDestino,
        BigDecimal monto,
        LocalDateTime fecha,
        String usuario,
        String descripcion,
        BigDecimal saldoCuentaOrigen,
        BigDecimal saldoCuentaDestino
) {
}
