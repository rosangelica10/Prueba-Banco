package hn.infatlan.prueba.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
   name = "bitacora"
)
@Getter
@Setter
public class BitacoraEntity {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   @Column(name = "id")
   private int id;

   @ManyToOne
   @JoinColumn(
      name = "id_cuenta",
      nullable = false
   )
   private CuentaEntity cuenta;

   @Enumerated(EnumType.STRING)
   @Column(name = "tipo_operacion", nullable = false)
   private TipoOperacion tipoOperacion;

   @Column(name = "monto", nullable = false)
   private BigDecimal monto;

   @Column(name = "saldo_anterior", nullable = false)
   private BigDecimal saldoAnterior;

   @Column(name = "saldo_nuevo", nullable = false)
   private BigDecimal saldoNuevo;

   @Column(name = "fecha", nullable = false)
   private LocalDateTime fecha;

   @Column(name = "usuario", nullable = false)
   private String usuario;

   @Column(name = "descripcion")
   private String descripcion;

   @ManyToOne
   @JoinColumn(name = "id_transferencia")
   private TransferenciaEntity transferencia;

   public BitacoraEntity() {
   }

}
