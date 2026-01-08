package com.musicplayer.nypmusicproje.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Playlist sınıfı, bir oynatma listesini temsil eder.
 * Model katmanında yer alır ve şarkı yönetiminden sorumludur.
 */
public class Playlist {

    // Oynatma listesinin adı
    private String name;

    // Listeye ait şarkılar (JavaFX ObservableList)
    private ObservableList<Song> songs;

    // Playlist oluşturucu
    public Playlist(String name) {
        this.name = name;
        this.songs = FXCollections.observableArrayList();
    }

    // Getter metodları
    public String getName() {
        return name;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }

    // Playlist adını günceller
    public void setName(String name) {
        this.name = name;
    }

    // Listeye şarkı ekler (aynı şarkı tekrar eklenmez)
    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    // Listeden şarkı siler
    public void removeSong(Song song) {
        songs.remove(song);
    }

    // Listedeki toplam şarkı sayısını döndürür
    public int getSongCount() {
        return songs.size();
    }

    // Playlist nesnesinin ekranda nasıl görüneceğini belirler (Overriding)
    @Override
    public String toString() {
        return name + " (" + songs.size() + " şarkı)";
    }

    // Playlist nesnelerinin karşılaştırılması için override edilir
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Playlist playlist = (Playlist) obj;
        return name.equals(playlist.name);
    }

    // equals metodu ile uyumlu hashCode üretir
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
