package com.example.cristofy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cristofy.entity.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
    
}
