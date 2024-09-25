package com.pragma.backend.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.infrastructure.entities.PlatoEntity;

@Repository
public interface PlatoEntityRepository extends JpaRepository<PlatoEntity, Long>{

}
