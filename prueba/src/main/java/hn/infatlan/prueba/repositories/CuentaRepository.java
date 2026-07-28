package hn.infatlan.prueba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.infatlan.prueba.entities.CuentaEntity;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    boolean existsByNumCuenta(String numCuenta);

    List<CuentaEntity> findByCliente_Id(int idCliente);

}
