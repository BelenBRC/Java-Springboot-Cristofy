package com.example.cristofy.controller;

import com.example.cristofy.entity.Artista;
import com.example.cristofy.service.ArtistaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @brief Clase que controla las peticiones relacionadas con los artistas
 */
@Controller
public class ArtistaController {
    private ArtistaService artistaService;

    /**
     * @brief Constructor de la clase ArtistaController
     * @param artistaService    Servicio de artista
     */
    public ArtistaController(ArtistaService artistaService) {
        super();
        this.artistaService = artistaService;
    }

    // Getters

    /**
     * @brief Método que devuelve el servicio de artista
     * @return artistaService    (ArtistaService)  Servicio de artista
     */
    public ArtistaService getArtistaService() {
        return artistaService;
    }


    // Métodos

    /**
     * @brief Método que devuelve la lista de artistas de la base de datos
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista de los artistas
     */
    @GetMapping("/artistas")
    public String listArtistas(Model modelo){
        modelo.addAttribute("artistas", getArtistaService().getAllArtistas());
        return "artistas";
    }
    
    /**
     * @brief Método que devuelve el formulario para crear un nuevo artista
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista del formulario para crear un nuevo artista
     */
    @GetMapping("/artistas/nuevo")
    public String newArtista(Model modelo){
        modelo.addAttribute("artista", new Artista());
        return "crear_artista";
    }

    /**
     * @brief Método que guarda un nuevo artista en la base de datos
     * @param artista   (Artista)   Artista a guardar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de artistas
     */
    @PostMapping("/artistas")
    public String saveArtista(@ModelAttribute Artista artista, RedirectAttributes ra){
        artista.setNombre_artista(artista.getNombre_artista().trim());
        try {
            getArtistaService().saveArtista(artista);
        } catch (Exception e) {
            System.out.println("Error al guardar el artista: " + e.getMessage());
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "El artista ya existe");
            }
            else{
                ra.addFlashAttribute("error", "Error al guardar el artista");
            }
            return "redirect:/artistas/nuevo";
        }
        return "redirect:/artistas";
    }

    /**
     * @brief Método que devuelve el formulario para actualizar un artista
     * @param id    (Long)  Id del artista a actualizar
     * @param modelo    (Model) Modelo de la vista
     * @return  String  Vista del formulario para actualizar un artista
     */
    @GetMapping("/artista/editar/{id}")
    public String editArtista(@PathVariable Long id, Model modelo){
        modelo.addAttribute("artista", getArtistaService().getArtistaById(id));
        return "editar_artista";
    }

    /**
     * @brief Método que actualiza un artista en la base de datos
     * @param id        (Long)  Id del artista a actualizar
     * @param artista   (Artista)   Artista a actualizar
     * @param ra        (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de artistas
     */
    @PostMapping("/artistas/{id}")
    public String updateArtista(@PathVariable Long id, @ModelAttribute Artista artista, RedirectAttributes ra){
        Artista artistaActual = getArtistaService().getArtistaById(id);

        artistaActual.setNombre_artista(artista.getNombre_artista().trim());

        try{
            getArtistaService().saveArtista(artistaActual);
        }
        catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                ra.addFlashAttribute("error", "El artista ya existe.");
            }
            else{
                ra.addFlashAttribute("error", "Error al modificar el artista.");
            }
            return "redirect:/artista/editar/" + id;
        }

        return "redirect:/artistas";
    }

    /**
     * @brief Método que elimina un artista de la base de datos
     * @param id    (Long)  Id del artista a eliminar
     * @param ra    (RedirectAttributes)  Atributos de redirección
     * @return  String  Redirección a la lista de artistas
     */
    @GetMapping("/artista/{id}")
    public String deleteArtista(@PathVariable Long id, RedirectAttributes ra){
        try{
            getArtistaService().deleteArtista(id);
        }
        catch(Exception e){
            ra.addFlashAttribute("error", "Error al eliminar el artista.");
        }

        return "redirect:/artistas";
    }
    
}
