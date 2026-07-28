package hn.infatlan.prueba.dto;

import java.util.List;

public record ClienteResponseDTO(
        int id,
        String nombre,
        String apellido,
        boolean activo,
        List<CuentaResumenDTO> cuentas
) {
}
