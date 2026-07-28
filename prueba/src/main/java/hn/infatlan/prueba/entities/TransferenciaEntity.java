package hn.infatlan.prueba.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "transferencia")
@Getter
@Setter
public class TransferenciaEntity {

   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   @Column(
      name = "id_transferencia"
   )
   private int idTransferencia;

   @ManyToOne
   @JoinColumn(
      name = "id_cuenta_origen",
      nullable = false
   )
   private CuentaEntity cuentaOrigen;

   @ManyToOne
   @JoinColumn(
      name = "id_cuenta_destino",
      nullable = false
   )
   private CuentaEntity cuentaDestino;

   @Column(name = "monto", nullable = false)
   private BigDecimal monto;

   @Column(name = "fecha", nullable = false)
   private LocalDateTime fecha;

   @Column(name = "usuario", nullable = false)
   private String usuario;

   @Column(name = "descripcion")
   private String descripcion;

   public TransferenciaEntity() {
   }

}
