package com.example.cristofy.service;

import java.util.List;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.service.implementation.PlaylistServiceImplementation;

/**
 * @brief Interfaz que define los métodos a implementar por la clase PlaylistServiceImplementation
 * @see PlaylistServiceImplementation
 */
public interface PlaylistService {
    /**
     * @brief Método que devuelve todas las playlists
     * @return  List<Playlist>    Lista de playlists de la base de datos
     */
    List<Playlist> getAllPlaylists();
    /**
     * @brief Método que guarda una playlist en la base de datos
     * @param playlist    (Playlist)    Playlist a guardar
     * @return  Playlist  Playlist guardada
     */
    Playlist savePlaylist(Playlist playlist);
    /**
     * @brief Método que actualiza una playlist en la base de datos
     * @param playlist    (Playlist)    Playlist a actualizar
     * @return  Playlist  Playlist actualizada
     */
    Playlist updatePlaylist(Playlist playlist);
    /**
     * @brief Método que devuelve una playlist por su id
     * @param id    (Long)  Id de la playlist
     * @return  Playlist    Playlist con el id especificado
     */
    Playlist getPlaylistById(Long id);
    /**
     * @brief Método que elimina una playlist de la base de datos
     * @param id    (Long)  Id de la playlist a eliminar
     */
    void deletePlaylist(Long id);
    /**
     * @brief Método que devuelve las canciones de una playlist
     * @param id    (Long)  Id de la playlist
     * @return  List<Cancion>   Lista de canciones de la playlist
     */
    List<Cancion> getCancionesPlaylist(Long id);
}
