package com.example.cristofy.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cristofy.entity.Cancion;
import com.example.cristofy.entity.Perfil;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.repository.PerfilRepository;
import com.example.cristofy.repository.PlaylistRepository;
import com.example.cristofy.service.PlaylistService;

/**
 * @brief Clase que implementa los métodos de la interfaz PlaylistService
 * @see PlaylistService
 */
@Service
public class PlaylistServiceImplementation implements PlaylistService{
    private PlaylistRepository playlistRepository;
    private PerfilRepository perfilRepository;

    /**
     * @brief Constructor de la clase PlaylistServiceImplementation
     * @param playlistRepository  Repositorio de playlists
     */
    public PlaylistServiceImplementation(PlaylistRepository playlistRepository, PerfilRepository perfilRepository) {
        super();
        this.playlistRepository = playlistRepository;
        this.perfilRepository = perfilRepository;
    }

    // Getters
    /**
     * @brief Método que devuelve el repositorio de playlists
     * @return playlistRepository    (PlaylistRepository)    Repositorio de playlists
     */
    public PlaylistRepository getPlaylistRepository() {
        return playlistRepository;
    }

    /**
     * @brief Método que devuelve el repositorio de perfiles
     * @return perfilRepository  (PerfilRepository)    Repositorio de perfiles
     */
    public PerfilRepository getPerfilRepository() {
        return perfilRepository;
    }

    // Métodos

    /**
     * @brief Método que devuelve todas las playlists
     * @return  List<Playlist>    Lista de playlists de la base de datos
     */
    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = getPlaylistRepository().findAll();
        List<Perfil> perfiles = getPerfilRepository().findAll();

        for(Playlist playlist : playlists){
            if(playlist.getCreador() == null){
                for(Perfil perfil : perfiles){
                    if(playlist.getId_creador() == perfil.getId_perfil()){
                        playlist.setCreador(perfil);
                    }
                }
                if(playlist.getCreador() == null){
                    for(Perfil perfil : perfiles){
                        if(perfil.getNombre_usuario().equals("desconocido")){
                            playlist.setCreador(perfil);
                        }
                    }
                }
            }
        }
        return playlists;
    }

    /**
     * @brief Método que guarda una playlist en la base de datos
     * @param playlist    (Playlist)    Playlist a guardar
     * @return  Playlist  Playlist guardada
     */
    @Override
    public Playlist savePlaylist(Playlist playlist) {
        if(playlist == null)
            return null;
        return getPlaylistRepository().save(playlist);
    }

    /**
     * @brief Método que actualiza una playlist en la base de datos
     * @param playlist    (Playlist)    Playlist a actualizar
     * @return  Playlist  Playlist actualizada
     */
    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        if(playlist == null)
            return null;
        playlist.actualizarNumCanciones();
        return getPlaylistRepository().save(playlist);
    }

    /**
     * @brief Método que devuelve una playlist por su id
     * @param id    (Long)  Id de la playlist
     * @return  Playlist    Playlist con el id especificado
     */
    @Override
    public Playlist getPlaylistById(Long id) {
        if(id == null)
            return null;
        return getPlaylistRepository().findById(id).get();
    }

    /**
     * @brief Método que elimina una playlist de la base de datos
     * @param id    (Long)  Id de la playlist a eliminar
     */
    @Override
    public void deletePlaylist(Long id) {
        if(id == null)
            return;
        //ELiminar las canciones de esa playlist para actualizar las estadísticas
        Playlist playlist = getPlaylistRepository().findById(id).get();
        List<Cancion> cancionesPlaylist = playlist.getListaCanciones();
        for(int i=0; i<cancionesPlaylist.size(); i++){
            playlist.removeCancion(cancionesPlaylist.get(i));
        }
        getPlaylistRepository().deleteById(id);
    }

    /**
     * @brief Método que devuelve las canciones de una playlist
     * @param id    (Long)  Id de la playlist
     * @return  List<Cancion>   Lista de canciones de la playlist
     */
    @Override
    public List<Cancion> getCancionesPlaylist(Long id) {
        if(id == null)
            return null;
        return getPlaylistRepository().findById(id).get().getListaCanciones();
    }

}
