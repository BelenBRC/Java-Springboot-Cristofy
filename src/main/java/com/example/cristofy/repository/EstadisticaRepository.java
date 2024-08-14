package com.example.cristofy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cristofy.entity.Estadistica;

@Repository
public interface EstadisticaRepository extends JpaRepository<Estadistica, Long>{
    
}
