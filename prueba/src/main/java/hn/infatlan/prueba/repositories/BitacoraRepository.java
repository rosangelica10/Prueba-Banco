package hn.infatlan.prueba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.infatlan.prueba.entities.BitacoraEntity;

public interface BitacoraRepository extends JpaRepository<BitacoraEntity, Integer> {

    List<BitacoraEntity> findByCuenta_IdCuentaOrderByFechaDesc(int idCuenta);

    List<BitacoraEntity> findAllByOrderByFechaDesc();

}
