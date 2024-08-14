package com.example.cristofy.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cristofy.entity.Perfil;
import com.example.cristofy.entity.Playlist;
import com.example.cristofy.repository.PerfilRepository;
import com.example.cristofy.repository.PlaylistRepository;
import com.example.cristofy.service.PerfilService;

/**
 * @brief Clase que implementa los métodos de la interfaz PerfilService
 * @see PerfilService
 */
@Service
public class PerfilServiceImplementation implements PerfilService{
    private PerfilRepository perfilRepository;
    private PlaylistRepository playlistRepository;

    /**
     * @brief Constructor de la clase PerfilServiceImplementation
     * @param perfilRepository  Repositorio de perfiles
     */
    public PerfilServiceImplementation(PerfilRepository perfilRepository, PlaylistRepository playlistRepository) {
        super();
        this.perfilRepository = perfilRepository;
        this.playlistRepository = playlistRepository;
    }

    // Getters
    /**
     * @brief Método que devuelve el repositorio de perfiles
     * @return perfilRepository    (PerfilRepository)    Repositorio de perfiles
     */
    public PerfilRepository getPerfilRepository() {
        return perfilRepository;
    }

    /**
     * @brief Método que devuelve el repositorio de playlists
     * @return playlistRepository  (PlaylistRepository)    Repositorio de playlists
     */
    public PlaylistRepository getPlaylistRepository() {
        return playlistRepository;
    }

    // Métodos

    /**
     * @brief Método que devuelve todos los perfiles
     * @return  List<Perfil>    Lista de perfiles de la base de datos
     */
    @Override
    public List<Perfil> getAllPerfiles() {
        List<Perfil> perfiles = getPerfilRepository().findAll();
        for(int i=0; i<perfiles.size(); i++){
            if(perfiles.get(i).getLogin().equals("desconocido")){
                perfiles.remove(i);
            }
        }
        return perfiles;
    }

    /**
     * @brief Método que guarda un perfil en la base de datos
     * @param perfil    (Perfil)    Perfil a guardar
     * @return  Perfil  Perfil guardado
     */
    @Override
    public Perfil savePerfil(Perfil perfil) {
        if(perfil == null)
            return null;
        return getPerfilRepository().save(perfil);
    }

    /**
     * @brief Método que actualiza un perfil en la base de datos
     * @param perfil    (Perfil)    Perfil a actualizar
     * @return  Perfil  Perfil actualizado
     */
    @Override
    public Perfil updatePerfil(Perfil perfil) {
        if(perfil == null)
            return null;
        return getPerfilRepository().save(perfil);
    }

    /**
     * @brief Método que devuelve un perfil por su id
     * @param id    (Long)  Id del perfil
     * @return  Perfil  Perfil con el id especificado
     */
    @Override
    public Perfil getPerfilById(Long id) {
        if(id == null)
            return null;
        return getPerfilRepository().findById(id).get();
    }

    /**
     * @brief Método que elimina un perfil de la base de datos
     * @param id    (Long)  Id del perfil a eliminar
     */
    @Override
    public void deletePerfil(Long id) {
        if(id == null)
            return;
        Perfil perfil = getPerfilRepository().findById(id).get();

        List<Long> ids = new ArrayList<>();
        for(Playlist playlist : perfil.getListaPlaylists()){
            ids.add(playlist.getId_playlist());
        }
        for(Long idPlaylist : ids){
            removePlaylistFromPerfil(id, idPlaylist);
        }

        getPerfilRepository().deleteById(id);
    }

    /**
     * @brief Método que añade una playlist a la lista de playlists del perfil
     * @param idPerfil      (Long)  Id del perfil
     * @param idPlaylist    (Long)  Id de la playlist
     */
    @Override
    public void addPlaylistToPerfil(Long idPerfil, Long idPlaylist) {
        if(idPerfil == null || idPlaylist == null)
            return;
        Perfil perfil = getPerfilRepository().findById(idPerfil).get();
        Playlist playlist = getPlaylistRepository().findById(idPlaylist).get();
        if(playlist == null || perfil == null)
            return;
        perfil.addPlaylist(playlist);
        getPerfilRepository().save(perfil);
        getPlaylistRepository().save(playlist);
    }

    /**
     * @brief Método que devuelve las playlists de un perfil
     * @param idPerfil  (Long)  Id del perfil
     * @return  List<Playlist>  Lista de playlists del perfil
     */
    @Override
    public List<Playlist> getPlaylistsPerfil(Long idPerfil) {
        if(idPerfil == null)
            return null;
        List<Playlist> playlistsPeril = getPerfilRepository().findById(idPerfil).get().getListaPlaylists();
        for(Perfil perfil : getPerfilRepository().findAll()){
            if(perfil.getId_perfil() == idPerfil){
                for(Playlist playlist : playlistsPeril){
                    playlist.setCreador(perfil);
                }
            }
        }
        return playlistsPeril;
    }

    /**
     * @brief Método que devuelve las playlists que no están en un perfil
     * @param idPerfil  (Long)  Id del perfil
     * @return  List<Playlist>  Lista de playlists que no están en el perfil
     */
    @Override
    public List<Playlist> getPlaylistsNotInPerfil(Long idPerfil) {
        if(idPerfil == null)
            return null;
        List<Playlist> allPlaylists = getPlaylistRepository().findAll();
        List<Playlist> playlistsPerfil = getPerfilRepository().findById(idPerfil).get().getListaPlaylists();
        allPlaylists.removeAll(playlistsPerfil);
        return allPlaylists;
    }

    /**
     * @brief Método que elimina una playlist de la lista de playlists del perfil
     * @param idPerfil      (Long)  Id del perfil
     * @param idPlaylist    (Long)  Id de la playlist
     */
    @Override
    public void removePlaylistFromPerfil(Long idPerfil, Long idPlaylist) {
        if(idPerfil == null || idPlaylist == null)
            return;
        Perfil perfil = getPerfilRepository().findById(idPerfil).get();
        Playlist playlist = getPlaylistRepository().findById(idPlaylist).get();
        if(playlist == null || perfil == null)
            return;
        perfil.removePlaylist(playlist);
        getPerfilRepository().save(perfil);
        getPlaylistRepository().save(playlist);
    }
    
}
