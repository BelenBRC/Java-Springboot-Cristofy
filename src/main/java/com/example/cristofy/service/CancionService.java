package com.example.cristofy.service;

import java.util.List;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.service.implementation.CancionServiceImplementation;

/**
 * @brief Interfaz que define los métodos a implementar por la clase CancionServiceImplementation
 * @see CancionServiceImplementation
 */
public interface CancionService{
    /**
     * @brief Método que devuelve todas las canciones
     * @return  List<Cancion>    Lista de canciones de la base de datos
     */
    List<Cancion> getAllCanciones();
    /**
     * @brief Método que guarda una canción en la base de datos
     * @param cancion    (Cancion)    Canción a guardar
     * @return  Cancion  Canción guardada
     */ 
    Cancion saveCancion(Cancion cancion);
    /**
     * @brief Método que actualiza una canción en la base de datos
     * @param cancion    (Cancion)    Canción a actualizar
     * @return  Cancion  Canción actualizada
     */
    Cancion updateCancion(Cancion cancion);
    /**
     * @brief Método que devuelve una canción por su id
     * @param id    (Long)  Id de la canción
     * @return  Cancion Canción con el id especificado
     */
    Cancion getCancionById(Long id);
    /**
     * @brief Método que elimina una canción de la base de datos
     * @param id    (Long)  Id de la canción
     */
    void deleteCancion(Long id);
}
