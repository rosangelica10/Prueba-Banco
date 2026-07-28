package hn.infatlan.prueba.dto;

import java.math.BigDecimal;

public record CuentaResumenDTO(
        int idCuenta,
        String numCuenta,
        BigDecimal saldo,
        boolean activo
) {
}
