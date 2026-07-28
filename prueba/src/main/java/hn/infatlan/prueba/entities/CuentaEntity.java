package hn.infatlan.prueba.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "cuenta")
@Getter
@Setter
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy =
        GenerationType.IDENTITY
    )
    @Column(name= "id_cuenta")
    private int idCuenta;

    @Column(name = "numCuenta", nullable = false, unique = true)
    private String numCuenta;
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

}
