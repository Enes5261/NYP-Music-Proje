package com.musicplayer.nypmusicproje.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {
    private String name;
    private ObservableList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = FXCollections.observableArrayList();
    }

    // Getters
    public String getName() { return name; }
    public ObservableList<Song> getSongs() { return songs; }

    // Setters
    public void setName(String name) { this.name = name; }

    // Şarkı yönetimi
    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public int getSongCount() {
        return songs.size();
    }

    @Override
    public String toString() {
        return name + " (" + songs.size() + " şarkı)";
    }
}