package hn.infatlan.prueba.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BitacoraResponseDTO(
        int id,
        int idCuenta,
        String numCuenta,
        String tipoOperacion,
        BigDecimal monto,
        BigDecimal saldoAnterior,
        BigDecimal saldoNuevo,
        LocalDateTime fecha,
        String usuario,
        String descripcion
) {
}
