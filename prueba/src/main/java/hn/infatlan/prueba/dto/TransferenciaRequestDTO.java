package hn.infatlan.prueba.dto;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(
        int idCuentaOrigen,
        int idCuentaDestino,
        BigDecimal monto,
        String usuario,
        String descripcion
) {
}
