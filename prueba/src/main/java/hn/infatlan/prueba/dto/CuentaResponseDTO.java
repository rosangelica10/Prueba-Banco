package hn.infatlan.prueba.dto;

import java.math.BigDecimal;

public record CuentaResponseDTO(
        int idCuenta,
        String numCuenta,
        BigDecimal saldo,
        boolean activo,
        ClienteResumenDTO cliente
) {
}
