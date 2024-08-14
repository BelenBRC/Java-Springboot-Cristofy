package com.example.cristofy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cristofy.entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
    
}
