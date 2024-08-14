package com.example.cristofy.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.repository.CancionRepository;
import com.example.cristofy.service.CancionService;

/**
 * @brief Clase que implementa los métodos de la interfaz CancionService
 * @see CancionService
 */
@Service
public class CancionServiceImplementation implements CancionService{
    private CancionRepository cancionRepository;

    /**
     * @brief Constructor de la clase CancionServiceImplementation
     * @param cancionRepository  Repositorio de canciones
     */
    public CancionServiceImplementation(CancionRepository cancionRepository) {
        super();
        this.cancionRepository = cancionRepository;
    }

    // Getters

    /**
     * @brief Método que devuelve el repositorio de canciones
     * @return cancionRepository    (CancionRepository)    Repositorio de canciones
     */
    public CancionRepository getCancionRepository() {
        return cancionRepository;
    }

    // Métodos

    /**
     * @brief Método que devuelve todas las canciones
     * @return  List<Cancion>    Lista de canciones de la base de datos
     */
    @Override
    public List<Cancion> getAllCanciones() {
        return getCancionRepository().findAll();
    }

    /**
     * @brief Método que guarda una canción en la base de datos
     * @param cancion    (Cancion)    Canción a guardar
     * @return  Cancion  Canción guardada
     */ 
    @Override
    public Cancion saveCancion(Cancion cancion) {
        if(cancion == null)
            return null;
        return getCancionRepository().save(cancion);
    }

    /**
     * @brief Método que actualiza una canción en la base de datos
     * @param cancion    (Cancion)    Canción a actualizar
     * @return  Cancion  Canción actualizada
     */
    @Override
    public Cancion updateCancion(Cancion cancion) {
        if(cancion == null)
            return null;
        return getCancionRepository().save(cancion);
    }

    /**
     * @brief Método que devuelve una canción por su id
     * @param id    (Long)  Id de la canción
     * @return  Cancion Canción con el id especificado
     */
    @Override
    public Cancion getCancionById(Long id) {
        if(id == null)
            return null;
        return getCancionRepository().findById(id).get();        
    }

    /**
     * @brief Método que elimina una canción de la base de datos
     * @param id    (Long)  Id de la canción
     */
    @Override
    public void deleteCancion(Long id) {
        if(id == null)
            return;
        getCancionRepository().deleteById(id);        
    }

}
