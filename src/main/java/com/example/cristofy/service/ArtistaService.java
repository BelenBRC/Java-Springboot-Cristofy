package com.example.cristofy.service;

import java.util.List;

import com.example.cristofy.entity.Artista;
import com.example.cristofy.service.implementation.ArtistaServiceImplementation;

/**
 * @brief Interfaz que define los métodos a implementar por la clase ArtistaServiceImplementation
 * @see ArtistaServiceImplementation
 */
public interface ArtistaService {
    /**
     * @brief Método que devuelve todos los artistas
     * @return  List<Artista>    Lista de artistas de la base de datos
     */
    List<Artista> getAllArtistas();
    /**
     * @brief Método que guarda un artista en la base de datos
     * @param artista    (Artista)    Artista a guardar
     * @return  Artista  Artista guardado
     */
    Artista saveArtista(Artista artista);
    /**
     * @brief Método que actualiza un artista en la base de datos
     * @param artista    (Artista)    Artista a actualizar
     * @return  Artista  Artista actualizado
     */
    Artista updateArtista(Artista artista);
    /**
     * @brief Método que devuelve un artista por su id
     * @param id    (Long)  Id del artista
     * @return  Artista  Artista con el id especificado
     */
    Artista getArtistaById(Long id);
    /**
     * @brief Método que elimina un artista de la base de datos
     * @param id    (Long)  Id del artista
     */
    void deleteArtista(Long id);
}
