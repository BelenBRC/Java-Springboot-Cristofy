package com.example.cristofy.service;

import java.util.List;

import com.example.cristofy.entity.Perfil;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.service.implementation.PerfilServiceImplementation;

/**
 * @brief Interfaz que define los métodos a implementar por la clase PerfilServiceImplementation
 * @see PerfilServiceImplementation
 */
public interface PerfilService{
    /**
     * @brief Método que devuelve todos los perfiles
     * @return  List<Perfil>    Lista de perfiles de la base de datos
     */
    List<Perfil> getAllPerfiles();
    /**
     * @brief Método que guarda un perfil en la base de datos
     * @param perfil    (Perfil)    Perfil a guardar
     * @return  Perfil  Perfil guardado
     */
    Perfil savePerfil(Perfil perfil);
    /**
     * @brief Método que actualiza un perfil en la base de datos
     * @param perfil    (Perfil)    Perfil a actualizar
     * @return  Perfil  Perfil actualizado
     */
    Perfil updatePerfil(Perfil perfil);
    /**
     * @brief Método que devuelve un perfil por su id
     * @param id    (Long)  Id del perfil
     * @return  Perfil  Perfil con el id especificado
     */
    Perfil getPerfilById(Long id);
    /**
     * @brief Método que elimina un perfil de la base de datos
     * @param id    (Long)  Id del perfil a eliminar
     */
    void deletePerfil(Long id);
    /**
     * @brief Método que añade una playlist a la lista de playlists del perfil
     * @param idPerfil      (Long)  Id del perfil
     * @param idPlaylist    (Long)  Id de la playlist
     */
    void addPlaylistToPerfil(Long idPerfil, Long idPlaylist);
    /**
     * @brief Método que devuelve las playlists de un perfil
     * @param idPerfil  (Long)  Id del perfil
     * @return  List<Playlist>  Lista de playlists del perfil
     */
    List<Playlist> getPlaylistsPerfil(Long idPerfil);
    /**
     * @brief Método que devuelve las playlists que no están en un perfil
     * @param idPerfil  (Long)  Id del perfil
     * @return  List<Playlist>  Lista de playlists que no están en el perfil
     */
    List<Playlist> getPlaylistsNotInPerfil(Long idPerfil);
    /**
     * @brief Método que elimina una playlist de la lista de playlists del perfil
     * @param idPerfil      (Long)  Id del perfil
     * @param idPlaylist    (Long)  Id de la playlist
     */
    void removePlaylistFromPerfil(Long idPerfil, Long idPlaylist);
}
