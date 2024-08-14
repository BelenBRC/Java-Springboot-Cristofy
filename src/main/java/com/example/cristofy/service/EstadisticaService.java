package com.example.cristofy.service;

import java.util.List;

import com.example.cristofy.entity.Estadistica;
import com.example.cristofy.service.implementation.EstadisticaServiceImplementation;

/**
 * @brief Interfaz que define los métodos a implementar por la clase EstadisticaServiceImplementation
 * @see EstadisticaServiceImplementation
 */
public interface EstadisticaService {
    /**
     * @brief Método que devuelve todas las estadísticas
     * @return  List<Estadistica>  Lista de estadísticas
     */
    List<Estadistica> getAllEstadisticas();
    /**
     * @brief Método que devuelve una estadística por su id
     * @param id    Id de la estadística
     * @return  Estadistica  Estadística
     */
    Estadistica getEstadisticaById(Long id);
    /**
     * @brief Método que guarda una estadística en la base de datos
     * @param estadistica    Estadística a guardar
     * @return  Estadistica  Estadística guardada
     */
    Estadistica saveEstadistica(Estadistica estadistica);
}
