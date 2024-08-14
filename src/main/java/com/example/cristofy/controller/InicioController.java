package com.example.cristofy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.cristofy.service.ArtistaService;
import com.example.cristofy.service.CancionService;
import com.example.cristofy.service.PerfilService;
import com.example.cristofy.service.PlaylistService;

/**
 * @brief Controlador de la página de inicio
 */
@Controller
public class InicioController {
    private ArtistaService artistaService;
    private CancionService cancionService;
    private PerfilService perfilService;
    private PlaylistService playlistService;

    /**
     * @brief Constructor de la clase InicioController
     * @param artistaService    Servicio de artista
     * @param cancionService    Servicio de canción
     * @param perfilService     Servicio de perfil
     * @param playlistService   Servicio de playlist
     */
    public InicioController(ArtistaService artistaService, CancionService cancionService, PerfilService perfilService, PlaylistService playlistService) {
        super();
        this.artistaService = artistaService;
        this.cancionService = cancionService;
        this.perfilService = perfilService;
        this.playlistService = playlistService;
    }

    /**
     * @brief Establece el servicio de artista
     * @param artistaService    Servicio de artista
     */
    public ArtistaService getArtistaService() {
        return artistaService;
    }

    /**
     * @brief Devuelve el servicio de canción
     * @param cancionService    Servicio de canción
     */
    public CancionService getCancionService() {
        return cancionService;
    }
    /**
     * @brief Devuelve el servicio de perfil
     * @param perfilService     Servicio de perfil
     */
    public PerfilService getPerfilService() {
        return perfilService;
    }

    /**
     * @brief Devuelve el servicio de playlist
     * @param playlistService   Servicio de playlist
     */
    public PlaylistService getPlaylistService() {
        return playlistService;
    }

    //Métodos
    @GetMapping("/inicio")
    public String inicio(Model modelo){
        modelo.addAttribute("artistas", getArtistaService().getAllArtistas());
        modelo.addAttribute("canciones", getCancionService().getAllCanciones());
        modelo.addAttribute("perfiles", getPerfilService().getAllPerfiles());
        modelo.addAttribute("playlists", getPlaylistService().getAllPlaylists());
        return "inicio";
    }
    
}
