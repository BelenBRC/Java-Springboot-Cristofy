package com.example.cristofy.controller;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.entity.Artista;
import com.example.cristofy.service.ArtistaService;
import com.example.cristofy.service.CancionService;
import com.example.cristofy.service.EstadisticaService;
import com.example.cristofy.service.PlaylistService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @brief Clase que controla las peticiones relacionadas con las canciones
 */
@Controller
public class CancionController {
    private CancionService cancionService;
    private EstadisticaService estadisticaService;
    private ArtistaService artistaService;
    private PlaylistService playlistService;

    /**
     * @brief Constructor de la clase CancionController
     * @param cancionService        Servicio de cancion
     * @param estadisticaService    Servicio de estadística
     * @param artistaService        Servicio de artista
     * @param playlistService       Servicio de playlist
     */
    public CancionController(CancionService cancionService, EstadisticaService estadisticaService, ArtistaService artistaService, PlaylistService playlistService) {
        super();
        this.cancionService = cancionService;
        this.estadisticaService = estadisticaService;
        this.artistaService = artistaService;
        this.playlistService = playlistService;
    }

    // Getters
    /**
     * @brief Método que devuelve el servicio de cancion
     * @return cancionService    (CancionService)  Servicio de cancion
     */
    public CancionService getCancionService() {
        return cancionService;
    }

    /**
     * @brief Método que devuelve el servicio de estadística
     * @return estadisticaService    (EstadisticaService)  Servicio de estadística
     */
    public EstadisticaService getEstadisticaService() {
        return estadisticaService;
    }

    /**
     * @brief Método que devuelve el servicio de artista
     * @return artistaService    (ArtistaService)  Servicio de artista
     */
    public ArtistaService getArtistaService() {
        return artistaService;
    }

    /**
     * @brief Método que devuelve el servicio de playlist
     * @return playlistService    (PlaylistService)  Servicio de playlist
     */
    public PlaylistService getPlaylistService() {
        return playlistService;
    }

    // Métodos

    /**
     * @brief Método que devuelve la lista de canciones de la base de datos
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de las canciones
     */
    @GetMapping("/canciones")
    public String listCanciones(Model modelo){
        modelo.addAttribute("canciones", getCancionService().getAllCanciones());
        return "canciones";
    }

    /**
     * @brief Método que devuelve el formulario para crear una nueva canción
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para crear una nueva canción
     */
    @GetMapping("/canciones/nueva")
    public String newCancion(Model modelo){
        Cancion cancion = new Cancion();
        List<Artista> artistas = getArtistaService().getAllArtistas();
        modelo.addAttribute("cancion", cancion);
        modelo.addAttribute("artistas", artistas);
        return "crear_cancion";
    }

    /**
     * @brief Método que guarda una nueva canción en la base de datos
     * @param cancion   (Cancion)    Canción a guardar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de las canciones
     */
    @PostMapping("/canciones")
    public String saveCancion(@ModelAttribute("cancion") Cancion cancion, RedirectAttributes ra){
        try{
            getCancionService().saveCancion(cancion);
            cancion.getEstadistica().setCancion(cancion);
            getEstadisticaService().saveEstadistica(cancion.getEstadistica());
        }
        catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "Esta canción ya existe.");
            }
            else{
                ra.addFlashAttribute("error", "Error al guardar la canción.");
            }
            return "redirect:/canciones/nueva";
        }        
        
        return "redirect:/canciones";
    }

    /**
     * @brief Método que devuelve el formulario para editar una canción
     * @param id        (Long)  Id de la canción a editar
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para editar una canción
     */
    @GetMapping("/cancion/editar/{id}")
    public String editCancion(@PathVariable Long id, Model modelo){
        modelo.addAttribute("cancion", getCancionService().getCancionById(id));
        return "editar_cancion";
    }

    /**
     * @brief Método que actualiza una canción en la base de datos
     * @param id        (Long)  Id de la canción a actualizar
     * @param cancion    (Cancion)    Canción a actualizar
     * @param modelo    (Model) Modelo de la vista
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de las canciones
     */
    @PostMapping("/canciones/{id}")
    public String updateCancion(@PathVariable Long id, @ModelAttribute("cancion") Cancion cancion, Model modelo, RedirectAttributes ra){
        Cancion cancionActual = getCancionService().getCancionById(id);

        cancionActual.setTitulo(cancion.getTitulo());
        cancionActual.setDuracion_seg(cancion.getDuracion_seg());
        cancionActual.setAnio_publicacion(cancion.getAnio_publicacion());

        try{
            getCancionService().updateCancion(cancionActual);
        }
        catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "Esta canción ya existe.");
            }
            else{
                ra.addFlashAttribute("error", "Error al actualizar la canción.");
            }
            return "redirect:/cancion/editar/" + id;
        }

        return "redirect:/canciones";
    }

    /**
     * @brief Método que elimina una canción de la base de datos
     * @param id    (Long)  Id de la canción a eliminar
     * @param ra    (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de las canciones
     */
    @GetMapping("/cancion/{id}")
    public String deleteCancion(@PathVariable Long id, RedirectAttributes ra){
        try{
            getCancionService().getCancionById(id).getListaPlaylists();
            List<Playlist> playlists = new ArrayList<>();
            for(Playlist p : getCancionService().getCancionById(id).getListaPlaylists()){
                playlists.add(p);
            }
            getCancionService().deleteCancion(id);
            for(Playlist p : playlists){
                p.actualizarNumCanciones();
                getPlaylistService().updatePlaylist(p);
            }
        }
        catch(Exception e){
            ra.addFlashAttribute("error", "Error al eliminar la canción.");
        }
        
        return "redirect:/canciones";
    }

}
