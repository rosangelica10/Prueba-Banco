package hn.infatlan.prueba.dto;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        String numCuenta,
        int idCliente,
        BigDecimal saldoInicial
) {
}
