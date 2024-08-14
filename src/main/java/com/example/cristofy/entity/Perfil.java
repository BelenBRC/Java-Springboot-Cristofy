package com.example.cristofy.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa la entidad Perfil
 * @author BelenBRC
 */
@Entity
@Table(name = "perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id_perfil;
    @Column(nullable = false)
    @Check(constraints = "LENGTH(nombre_usuario) > 3 AND LENGTH(nombre_usuario) < 30")
    private String  nombre_usuario;
    @Column(unique = true, nullable = false)
    @Check(constraints = "LENGTH(login) > 3 AND LENGTH(login) < 20")
    private String  login;
    @Column(nullable = false)
    @Check(constraints = "LENGTH(contrasenia) > 3 AND LENGTH(contrasenia) < 40")
    private String  contrasenia;
    @Column(unique = true, nullable = false)
    @Check(constraints = "LENGTH(email) > 4 AND LENGTH(email) < 40")
    private String  email;
    @ManyToMany(mappedBy = "listaPerfiles")
    private List<Playlist> listaPlaylists;

    /**
     * @brief Constructor por defecto de la clase Perfil
     */
    public Perfil() {
        setListaPlaylists(new ArrayList<Playlist>());
    }

    /**
     * @brief Constructor de la clase Perfil con parámetros
     * @param nombre_usuario    Nombre del usuario
     * @param login             Nombre de usuario para iniciar sesión
     * @param contrasenia       Contraseña del usuario
     * @param email             Correo electrónico del usuario
     */
    public Perfil(String nombre_usuario, String login, String contrasenia, String email, Integer anio_creacion) {
        setNombre_usuario(nombre_usuario);
        setLogin(login);
        setContrasenia(contrasenia);
        setEmail(email);
        setListaPlaylists(new ArrayList<Playlist>());
    }

    // Getters y Setters
    /**
     * @brief Método que devuelve el id del perfil
     * @return  id_perfil   (Long)  Id del perfil
     */
    public Long getId_perfil() {
        return id_perfil;
    }

    /**
     * @brief Método que establece el id del perfil
     * @param id_perfil (Long)  Id del perfil
     */
    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    /**
     * @brief Método que devuelve el nombre del usuario
     * @return  nombre_usuario  (String)    Nombre del usuario
     */
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    /**
     * @brief Método que establece el nombre del usuario
     * @param nombre_usuario    (String)    Nombre del usuario
     */
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    /**
     * @brief Método que devuelve el nombre de usuario para iniciar sesión
     * @return  login   (String)    Nombre de usuario para iniciar sesión
     */
    public String getLogin() {
        return login;
    }

    /**
     * @brief Método que establece el nombre de usuario para iniciar sesión
     * @param login (String)    Nombre de usuario para iniciar sesión
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @brief Método que devuelve la contraseña del usuario
     * @return  contrasenia (String)    Contraseña del usuario
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @brief Método que establece la contraseña del usuario
     * @param contrasenia   (String)    Contraseña del usuario
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @brief Método que devuelve el correo electrónico del usuario
     * @return  email   (String)    Correo electrónico del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Método que establece el correo electrónico del usuario
     * @param email (String)    Correo electrónico del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Método que devuelve la lista de playlists del perfil
     * @return  listaPlaylists  (List<Playlist>) Lista de playlists del perfil
     */
    public List<Playlist> getListaPlaylists() {
        return listaPlaylists;
    }

    /**
     * @brief Método que establece la lista de playlists del perfil
     * @param listaPlaylists    (List<Playlist>) Lista de playlists del perfil
     */
    public void setListaPlaylists(List<Playlist> listaPlaylists) {
        this.listaPlaylists = listaPlaylists;
    }

    // Métodos

    /**
     * @brief Método que añade una playlist a la lista de playlists del perfil
     * @param playlist   (Playlist)  Playlist a añadir
     */
    public void addPlaylist(Playlist playlist){
        getListaPlaylists().add(playlist);
        playlist.getListaPerfiles().add(this);
    }

    /**
     * @brief Método que elimina una playlist de la lista de playlists del perfil
     * @param playlist   (Playlist)  Playlist a eliminar
     */
    public void removePlaylist(Playlist playlist){
        getListaPlaylists().remove(playlist);
        playlist.getListaPerfiles().remove(this);
    }

}
