package com.pragma.backend.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.infrastructure.entities.PedidoEntity;

@Repository
public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long>{

	List<PedidoEntity> findByidCliente(Long idCliente);
}
