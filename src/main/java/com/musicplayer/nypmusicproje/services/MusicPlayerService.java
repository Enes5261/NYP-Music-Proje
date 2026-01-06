package com.musicplayer.nypmusicproje.services;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MusicPlayerService {
    private static MusicPlayerService instance;

    private ObservableList<Song> allSongs;
    private ObservableList<Playlist> playlists;
    private Song currentSong;
    private boolean isPlaying;

    private MusicPlayerService() {
        allSongs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        isPlaying = false;
        initializeSampleData();
    }

    public static MusicPlayerService getInstance() {
        if (instance == null) {
            instance = new MusicPlayerService();
        }
        return instance;
    }

    private void initializeSampleData() {
        // 10 örnek şarkı
        allSongs.add(new Song("Midnight Dreams", "Luna Park", "Nightscape", "3:45", ""));
        allSongs.add(new Song("Electric Sunset", "Neon Waves", "Horizon", "4:12", ""));
        allSongs.add(new Song("Lost in Tokyo", "Urban Echo", "City Lights", "3:28", ""));
        allSongs.add(new Song("Ocean Drive", "Coastal Vibes", "Summer Days", "4:01", ""));
        allSongs.add(new Song("Starlight", "Aurora Sky", "Celestial", "3:55", ""));
        allSongs.add(new Song("Neon Nights", "Synthwave City", "Retro Future", "4:23", ""));
        allSongs.add(new Song("Moonlight Sonata", "Classical Dreams", "Piano Collection", "5:12", ""));
        allSongs.add(new Song("Digital Paradise", "Cyber Pulse", "Matrix", "3:47", ""));
        allSongs.add(new Song("Sunset Boulevard", "California Dreams", "West Coast", "4:05", ""));
        allSongs.add(new Song("Northern Lights", "Arctic Sound", "Frozen", "4:30", ""));

        // Örnek oynatma listesi
        Playlist favorites = new Playlist("Favorilerim");
        favorites.addSong(allSongs.get(0));
        favorites.addSong(allSongs.get(2));
        favorites.addSong(allSongs.get(4));
        playlists.add(favorites);

        Playlist chill = new Playlist("Chill Vibes");
        chill.addSong(allSongs.get(3));
        chill.addSong(allSongs.get(6));
        playlists.add(chill);
    }

    // Şarkı yönetimi
    public ObservableList<Song> getAllSongs() {
        return allSongs;
    }

    public void addSong(Song song) {
        allSongs.add(song);
    }

    // Oynatma listesi yönetimi
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    // Müzik oynatma kontrolü
    public void play(Song song) {
        currentSong = song;
        isPlaying = true;
        // Gerçek müzik çalma işlemi burada yapılacak
        System.out.println("Şarkı çalınıyor: " + song.getTitle());
    }

    public void pause() {
        isPlaying = false;
        System.out.println("Müzik duraklatıldı");
    }

    public void stop() {
        isPlaying = false;
        currentSong = null;
        System.out.println("Müzik durduruldu");
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}