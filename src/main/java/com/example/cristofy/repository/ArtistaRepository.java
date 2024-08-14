package com.example.cristofy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cristofy.entity.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>{
    
}
