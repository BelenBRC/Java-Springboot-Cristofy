package com.example.cristofy.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cristofy.entity.Artista;
import com.example.cristofy.repository.ArtistaRepository;
import com.example.cristofy.service.ArtistaService;

/**
 * @brief Clase que implementa los métodos de la interfaz ArtistaService
 * @see ArtistaService
 */
@Service
public class ArtistaServiceImplementation implements ArtistaService{
    private ArtistaRepository artistaRepository;

    /**
     * @brief Constructor de la clase ArtistaServiceImplementation
     * @param artistaRepository  Repositorio de artistas
     */
    public ArtistaServiceImplementation(ArtistaRepository artistaRepository) {
        super();
        this.artistaRepository = artistaRepository;
    }

    // Getters

    /**
     * @brief Método que devuelve el repositorio de artistas
     * @return artistaRepository    (ArtistaRepository)    Repositorio de artistas
     */
    public ArtistaRepository getArtistaRepository() {
        return artistaRepository;
    }

    // Métodos

    /**
     * @brief Método que devuelve todos los artistas
     * @return  List<Artista>    Lista de artistas de la base de datos
     */
    @Override
    public List<Artista> getAllArtistas() {
        return getArtistaRepository().findAll();        
    }

    /**
     * @brief Método que guarda un artista en la base de datos
     * @param artista    (Artista)    Artista a guardar
     * @return  Artista  Artista guardado
     */
    @Override
    public Artista saveArtista(Artista artista) {
        if(artista == null)
            return null;
        return getArtistaRepository().save(artista);
    }

    /**
     * @brief Método que actualiza un artista en la base de datos
     * @param artista    (Artista)    Artista a actualizar
     * @return  Artista  Artista actualizado
     */
    @Override
    public Artista updateArtista(Artista artista) {
        if(artista == null)
            return null;
        return getArtistaRepository().save(artista);
    }

    /**
     * @brief Método que devuelve un artista por su id
     * @param id    (Long)  Id del artista
     * @return  Artista  Artista con el id especificado
     */
    @Override
    public Artista getArtistaById(Long id) {
        if(id == null)
            return null;
        return getArtistaRepository().findById(id).get();
    }

    /**
     * @brief Método que elimina un artista de la base de datos
     * @param id    (Long)  Id del artista
     */
    @Override
    public void deleteArtista(Long id) {
        if(id == null)
            return;
        getArtistaRepository().deleteById(id);
    }

}