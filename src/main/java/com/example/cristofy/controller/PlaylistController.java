package com.example.cristofy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.entity.Perfil;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.service.CancionService;
import com.example.cristofy.service.PerfilService;
import com.example.cristofy.service.PlaylistService;

/**
 * @brief Clase que controla las peticiones relacionadas con las playlists
 */
@Controller
public class PlaylistController {
    private PlaylistService playlistService;
    private CancionService cancionService;
    private PerfilService perfilService;
    
    /**
     * @brief Constructor de la clase PlaylistController
     * @param playlistService   Servicio de playlists
     * @param cancionService    Servicio de canciones
     * @param perfilService     Servicio de usuario
     */
    public PlaylistController(PlaylistService playlistService, CancionService cancionService, PerfilService perfilService) {
        super();
        this.playlistService = playlistService;
        this.cancionService = cancionService;
        this.perfilService = perfilService;
    }

    // Getters
    /**
     * @brief Método que devuelve el servicio de playlists
     * @return playlistService  (PlaylistService)    Servicio de playlists
     */
    public PlaylistService getPlaylistService() {
        return playlistService;
    }

    /**
     * @brief Método que devuelve el servicio de canciones
     * @return cancionService   (CancionService)    Servicio de canciones
     */
    public CancionService getCancionService() {
        return cancionService;
    }

    /**
     * @brief Método que devuelve el servicio de usuario
     * @return perfilService    (PerfilService) Servicio de usuario
     */
    public PerfilService getPerfilService() {
        return perfilService;
    }

    // Métodos

    /**
     * @brief Método que devuelve la lista de playlists de la base de datos
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de las playlists
     */
    @GetMapping("/playlists")
    public String listPlaylists(Model modelo){
        modelo.addAttribute("playlists", getPlaylistService().getAllPlaylists());
        return "playlists";
    }

    /**
     * @brief Método que devuelve el formulario para crear una nueva playlist
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para crear una playlist
     */
    @GetMapping("/playlists/nueva")
    public String nuevaPlaylist(Model modelo){
        Playlist playlist = new Playlist();
        List<Perfil> perfiles = getPerfilService().getAllPerfiles();
        modelo.addAttribute("playlist", playlist);
        modelo.addAttribute("perfiles", perfiles);
        return "crear_playlist";
    }

    /**
     * @brief Método que guarda una playlist en la base de datos
     * @param playlist  (Playlist)  Playlist a guardar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de playlists
     */
    @PostMapping("/playlists")
    public String savePlaylist(@ModelAttribute("playlist") Playlist playlist, RedirectAttributes ra){
        try{
            getPlaylistService().savePlaylist(playlist);
        }
        catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "Ya existe una playlist con ese nombre para este usuario.");
            }
            else{
                ra.addFlashAttribute("error", "Error al guardar la playlist.");
            }
            return "redirect:/playlists/nueva";
        }
        return "redirect:/playlists";
    }

    /**
     * @brief Método que devuelve el formulario para editar una playlist
     * @param id        (Long)  Identificador de la playlist
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para editar una playlist
     */
    @GetMapping("/playlist/editar/{id}")
    public String editarPlaylist(@PathVariable Long id, Model modelo){
        //Buscar canciones que no estén en la playlist
        List<Cancion> cancionesSistema = getCancionService().getAllCanciones();
        Playlist playlist = getPlaylistService().getPlaylistById(id);
        List<Cancion> cancionesPlaylist = playlist.getListaCanciones();
        cancionesSistema.removeAll(cancionesPlaylist);

        modelo.addAttribute("cancionesSistema", cancionesSistema);
        modelo.addAttribute("cancionesPlaylist", cancionesPlaylist);
        modelo.addAttribute("playlist", playlist);

        return "editar_playlist";
    }

    /**
     * @brief Método que actualiza una playlist en la base de datos
     * @param id        (Long)      Identificador de la playlist
     * @param playlist  (Playlist)  Playlist a actualizar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de playlists
     */
    @PostMapping("/playlists/{id}")
    public String updatePlaylist(@PathVariable Long id, @ModelAttribute("playlist") Playlist playlist, RedirectAttributes ra){
        Playlist playlistToUpdate = getPlaylistService().getPlaylistById(id);

        playlistToUpdate.setNombre_playlist(playlist.getNombre_playlist());

        try{
            getPlaylistService().updatePlaylist(playlistToUpdate);
            getPerfilService().getAllPerfiles();
        }
        catch(Exception e){
            System.out.println("Error al modificar la playlist: " + e.getMessage());
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "Ya existe una playlist con ese nombre para este usuario.");
            }
            else{
                ra.addFlashAttribute("error", "Error al modificar la playlist.");
            }
            return "redirect:/playlist/editar/" + id;
        }

        return "redirect:/playlists";
    }

    /**
     * @brief Método que elimina una playlist de la base de datos
     * @param id    (Long)  Identificador de la playlist
     * @return  String  Redirección a la lista de playlists
     */
    @GetMapping("/playlist/{id}")
    public String deletePlaylist(@PathVariable Long id){
        getPlaylistService().deletePlaylist(id);
        
        return "redirect:/playlists";
    }

    /**
     * @brief Método que guarda una canción en una playlist
     * @param id        (Long)      Identificador de la playlist
     * @param cancionId (Long)      Identificador de la canción
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de editar la playlist
     */
    @PostMapping("/playlist/nuevaCancion/{id}")
    public String saveCancionPlaylist(@PathVariable Long id, @RequestParam("id_cancion") Long cancionId, RedirectAttributes ra){
        Playlist playlist = getPlaylistService().getPlaylistById(id);
        Cancion cancion = getCancionService().getCancionById(cancionId);
        playlist.addCancion(cancion);
        
        try{
            getPlaylistService().updatePlaylist(playlist);
            getCancionService().updateCancion(cancion);
        }
        catch(Exception e){
            ra.addFlashAttribute("error2", "Error al añadir la canción a la playlist.");
            return "redirect:/playlist/editar/{id}";
        }

        return "redirect:/playlist/editar/{id}";
    }

    /**
     * @brief Método que elimina una canción de una playlist
     * @param id        (Long)      Identificador de la playlist
     * @param cancionId (Long)      Identificador de la canción
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de editar la playlist
     */
    @GetMapping("/playlist/{id}/eliminarCancion/{cancionId}")
    public String deleteCancionPlaylist(@PathVariable Long id, @PathVariable Long cancionId, RedirectAttributes ra){
        Playlist playlist = getPlaylistService().getPlaylistById(id);
        Cancion cancion = getCancionService().getCancionById(cancionId);
        try{
            playlist.removeCancion(cancion);
            getPlaylistService().updatePlaylist(playlist);
            getCancionService().updateCancion(cancion);
        }
        catch(Exception e){
            ra.addFlashAttribute("error3", "No se ha podido eliminar la canción de la playlist.");
        }

        return "redirect:/playlist/editar/{id}";
    }

}
