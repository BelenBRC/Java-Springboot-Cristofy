package com.example.cristofy.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cristofy.entity.Estadistica;
import com.example.cristofy.repository.EstadisticaRepository;
import com.example.cristofy.service.EstadisticaService;

/**
 * @brief Clase que implementa los métodos de la interfaz EstadisticaService
 * @see EstadisticaService
 */
@Service
public class EstadisticaServiceImplementation implements EstadisticaService{
    private EstadisticaRepository estadisticaRepository;

    /**
     * @brief Constructor de la clase EstadisticaServiceImplementation
     * @param estadisticaRepository Repositorio de estadísticas
     */
    public EstadisticaServiceImplementation(EstadisticaRepository estadisticaRepository) {
        super();
        this.estadisticaRepository = estadisticaRepository;
    }
    
    // Getters y Setters
    /**
     * @brief Método que devuelve el repositorio de estadísticas
     * @return  estadisticaRepository  (EstadisticaRepository)  Repositorio de estadísticas
     */
    public EstadisticaRepository getEstadisticaRepository() {
        return estadisticaRepository;
    }

    /**
     * @brief Método que devuelve todas las estadísticas
     * @return  List<Estadistica>  Lista de estadísticas
     */
    @Override
    public List<Estadistica> getAllEstadisticas() {
        return estadisticaRepository.findAll();
    }

    /**
     * @brief Método que devuelve una estadística por su id
     * @param id    Id de la estadística
     * @return  Estadistica  Estadística
     */
    @Override
    public Estadistica getEstadisticaById(Long id) {
        if(id == null)
            return null;
        return estadisticaRepository.findById(id).get();
    }

    /**
     * @brief Método que guarda una estadística en la base de datos
     * @param estadistica    Estadística a guardar
     * @return  Estadistica  Estadística guardada
     */
    @Override
    public Estadistica saveEstadistica(Estadistica estadistica) {
        if(estadistica == null)
            return null;
        return estadisticaRepository.save(estadistica);
    }

}
