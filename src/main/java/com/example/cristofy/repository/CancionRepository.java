package com.example.cristofy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cristofy.entity.Cancion;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long>{
    
}
