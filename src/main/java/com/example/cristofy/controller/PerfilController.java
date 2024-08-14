package com.example.cristofy.controller;

import com.example.cristofy.entity.Perfil;
import com.example.cristofy.service.PerfilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @brief Clase que controla las peticiones relacionadas con los perfiles de usuario
 */
@Controller
public class PerfilController {
    private PerfilService perfilService;

    /**
     * @brief Constructor de la clase PerfilController
     * @param perfilService    Servicio de usuario
     */
    public PerfilController(PerfilService perfilService) {
        super();
        this.perfilService = perfilService;
    }

    // Getters
    /**
     * @brief Método que devuelve el servicio de usuario
     * @return perfilService    (PerfilService)  Servicio de usuario
     */
    public PerfilService getPerfilService() {
        return perfilService;
    }

    // Métodos

    /**
     * @brief Método que devuelve la lista de perfiles de la base de datos
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de los perfiles
     */
    @GetMapping("/perfiles")
    public String listPerfiles(Model modelo){
        modelo.addAttribute("perfiles", getPerfilService().getAllPerfiles());
        return "perfiles";
    }

    /**
     * @brief Método que devuelve el formulario para crear un nuevo perfil
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para crear un nuevo perfil
     */
    @GetMapping("/perfiles/nuevo")
    public String newPerfil(Model modelo){
        Perfil perfil = new Perfil();
        modelo.addAttribute("perfil", perfil);
        return "crear_perfil";
    }

    /**
     * @brief Método que guarda un nuevo perfil en la base de datos
     * @param perfil    (Perfil)    Perfil a guardar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de los perfiles 
     *                  o al formulario para crear un nuevo perfil en caso de error
     */
    @PostMapping("/perfiles")
    public String savePerfil(@ModelAttribute("perfil") Perfil perfil, RedirectAttributes ra){
        try{
            getPerfilService().savePerfil(perfil);
        }
        catch(Exception e){
            System.out.println("Error al guardar el perfil: " + e.getMessage());
            if(e.getMessage().contains("@")){
                ra.addFlashAttribute("error", "El email ya está en uso.");
            }
            else{
                ra.addFlashAttribute("error", "El login ya está en uso.");
            }
            return "redirect:/perfiles/nuevo";
        }
        return "redirect:/perfiles";
    }

    /**
     * @brief Método que devuelve el formulario para editar un perfil
     * @param id        (Long)  Id del perfil a editar
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista para editar un perfil
     */
    @GetMapping("/perfil/editar/{id}")
    public String editPerfil(@PathVariable Long id, Model modelo){
        modelo.addAttribute("perfil", getPerfilService().getPerfilById(id));
        return "editar_perfil";
    }

    /**
     * @brief Método que actualiza un perfil en la base de datos
     * @param id        (Long)  Id del perfil a actualizar
     * @param perfil    (Perfil)    Perfil a actualizar
     * @param modelo    (Model) Modelo de la vista
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de los perfiles
     */
    @PostMapping("/perfiles/{id}")
    public String updatePerfil(@PathVariable Long id, @ModelAttribute("perfil") Perfil perfil, Model modelo, RedirectAttributes ra){
        Perfil perfilActual = getPerfilService().getPerfilById(id);

        perfilActual.setNombre_usuario(perfil.getNombre_usuario());
        perfilActual.setLogin(perfil.getLogin());
        if(perfil.getContrasenia() != null && !perfil.getContrasenia().isEmpty())
            perfilActual.setContrasenia(perfil.getContrasenia());
        perfilActual.setEmail(perfil.getEmail());

        try{
            getPerfilService().updatePerfil(perfilActual);
        }
        catch(Exception e){
            if(e.getMessage().contains("@")){
                ra.addFlashAttribute("error", "El email ya está en uso.");
            }
            else{
                ra.addFlashAttribute("error", "El login ya está en uso.");
            }
            return "redirect:/perfil/editar/" + id;
        }

        return "redirect:/perfiles";
    }

    /**
     * @brief Método que elimina un perfil de la base de datos
     * @param id    (Long)  Id del perfil a eliminar
     * @param ra    (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de los perfiles
     */
    @GetMapping("/perfil/{id}")
    public String deletePerfil(@PathVariable Long id, RedirectAttributes ra){
        try{
            getPerfilService().deletePerfil(id);
        }
        catch(Exception e){
            ra.addFlashAttribute("error", "Error al eliminar el perfil.");
            return "redirect:/perfiles";
        }
        
        return "redirect:/perfiles";
    }

    /**
     * @brief Método que devuelve el formulario para gestionar las playlists de un perfil
     * @param idPerfil  (Long)  Id del perfil
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de las playlists de un perfil
     */
    @GetMapping("/perfil/{idPerfil}/playlists")
    public String gestionarPlaylistsPerfil(@PathVariable Long idPerfil, Model modelo){
        modelo.addAttribute("perfil", getPerfilService().getPerfilById(idPerfil));
        modelo.addAttribute("playlists", getPerfilService().getPlaylistsPerfil(idPerfil));
        modelo.addAttribute("allPlaylists", getPerfilService().getPlaylistsNotInPerfil(idPerfil));
        return "playlists_perfil";
    }

    /**
     * @brief Método que devuelve el formulario para añadir una playlist a un perfil
     * @param id        (Long)  Id del perfil
     * @param modelo    (Model) Modelo de la vista
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Vista para añadir una playlist a un perfil
     */
    @PostMapping("/perfil/nuevaPlaylist/{id}")
    public String savePlaylistPerfil(@PathVariable Long id, @RequestParam("id_playlist") Long id_playlist, RedirectAttributes ra){
        try{
            getPerfilService().addPlaylistToPerfil(id, id_playlist);
        }
        catch(Exception e){
            ra.addFlashAttribute("error", "Error al añadir la playlist al perfil.");
            return "redirect:/perfil/" + id + "/playlists";
        }

        return "redirect:/perfil/" + id + "/playlists";
    }

    /**
     * @brief Método que elimina una playlist de un perfil
     * @param idPerfil      (Long)  Id del perfil
     * @param idPlaylist    (Long)  Id de la playlist
     * @param ra            (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la vista de las playlists de un perfil
     */
    @GetMapping("/perfil/{idPerfil}/removePlaylist/{idPlaylist}")
    public String removePlaylistPerfil(@PathVariable Long idPerfil, @PathVariable Long idPlaylist, RedirectAttributes ra){
        try{
            getPerfilService().removePlaylistFromPerfil(idPerfil, idPlaylist);
        }
        catch(Exception e){
            ra.addFlashAttribute("error2", "Error al eliminar la playlist del perfil.");
            return "redirect:/perfil/" + idPerfil + "/playlists";
        }
        return "redirect:/perfil/" + idPerfil + "/playlists";
    }

}
