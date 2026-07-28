package hn.infatlan.prueba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.infatlan.prueba.entities.ClienteEntity;


public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer>  {
    
}
