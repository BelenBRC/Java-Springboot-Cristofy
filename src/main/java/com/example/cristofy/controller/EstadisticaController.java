package com.example.cristofy.controller;

import com.example.cristofy.service.EstadisticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @brief Clase que controla las peticiones relacionadas con las estadísticas
 */
@Controller
public class EstadisticaController {
    private EstadisticaService estadisticaService;

    /**
     * @brief Constructor de la clase EstadisticaController
     * @param estadisticaService    Servicio de estadística
     */
    public EstadisticaController(EstadisticaService estadisticaService) {
        super();
        this.estadisticaService = estadisticaService;
    }

    // Getters
    /**
     * @brief Método que devuelve el servicio de estadística
     * @return estadisticaService    (EstadisticaService)  Servicio de estadística
     */
    public EstadisticaService getEstadisticaService() {
        return estadisticaService;
    }

    // Métodos

    /**
     * @brief Método que devuelve la lista de estadísticas de la base de datos
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de las estadísticas
     */
    @GetMapping("/estadisticas")
    public String listEstadisticas(Model modelo){
        modelo.addAttribute("estadisticas", getEstadisticaService().getAllEstadisticas());
        return "estadisticas";
    }
}
