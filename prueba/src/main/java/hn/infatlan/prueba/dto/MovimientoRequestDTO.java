package hn.infatlan.prueba.dto;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        int idCuenta,
        BigDecimal monto,
        String usuario,
        String descripcion
) {
}
