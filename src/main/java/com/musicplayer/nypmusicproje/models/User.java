package com.musicplayer.nypmusicproje.models;

import java.util.ArrayList;
import java.util.List;

/**
 * User sınıfı, uygulamadaki bir kullanıcıyı temsil eder.
 * Kullanıcı bilgileri ve kullanıcıya ait oynatma listeleri burada tutulur.
 */
public class User {

    private String username;
    private String password;
    private List<Playlist> playlists; // Kullanıcıya ait oynatma listeleri

    // Parametresiz constructor (JSON / framework uyumluluğu)
    public User() {
        this.playlists = new ArrayList<>();
    }

    // Temel kullanıcı oluşturma constructor'ı
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.playlists = new ArrayList<>();
    }

    // Getter & Setter metodları
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    // Kullanıcıya yeni playlist ekler
    public void addPlaylist(Playlist playlist) {
        if (!playlists.contains(playlist)) {
            playlists.add(playlist);
        }
    }

    // Playlist ismi ile ekleme (OVERLOADING)
    public void addPlaylist(String playlistName) {
        addPlaylist(new Playlist(playlistName));
    }

    // Playlist silme
    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    // İsim üzerinden playlist bulma
    public Playlist getPlaylistByName(String name) {
        return playlists.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Kullanıcıyı okunabilir şekilde temsil eder (OVERRIDING)
    @Override
    public String toString() {
        return "User: " + username + " (Playlists: " + playlists.size() + ")";
    }

    // Kullanıcılar username üzerinden karşılaştırılır (OVERRIDING)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return username.equals(user.username);
    }

    // equals metodu ile uyumlu hashCode
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
