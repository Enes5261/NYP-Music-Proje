package com.musicplayer.nypmusicproje.services;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.models.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayerService {
    private static MusicPlayerService instance;

    private ObservableList<Song> allSongs;
    private ObservableList<Playlist> playlists;

    // ✅ Song'u Property olarak tanımla
    private ObjectProperty<Song> currentSongProperty;

    private boolean isPlaying;
    private User currentUser;

    private MediaPlayer mediaPlayer;

    private MusicPlayerService() {
        allSongs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();

        // ✅ Property'yi başlat
        currentSongProperty = new SimpleObjectProperty<>();

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
        allSongs.add(new Song("Kaç Kadeh Kırıldı", "Müslüm Gürses", "Arabesk", "4:05",
                "/com/musicplayer/nypmusicproje/ses/kac-kadeh-kirildi.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/kac-kadeh-kirildi.jpg"));

        allSongs.add(new Song("COOOK PARDON", "Lvbel C5", "Rap", "3:40",
                "/com/musicplayer/nypmusicproje/ses/coook-pardon.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/coook-pardon.jpg"));

        allSongs.add(new Song("Dom Dom Kurşunu", "İbrahim Tatlıses", "Arabesk", "3:35",
                "/com/musicplayer/nypmusicproje/ses/dom-dom-kursunu.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/dom-dom-kursunu.jpg"));

        allSongs.add(new Song("Rüya", "Manifest", "Pop", "3:50",
                "/com/musicplayer/nypmusicproje/ses/ruya.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/ruya.jpg"));

        allSongs.add(new Song("Zalim", "Sezen Aksu", "Arabesk", "4:00",
                "/com/musicplayer/nypmusicproje/ses/zalim.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/zalim.jpg"));

        allSongs.add(new Song("Kusura Bakma", "BLOK3", "Arabesk", "3:45",
                "/com/musicplayer/nypmusicproje/ses/kusura-bakma.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/kusura-bakma.jpg"));

        allSongs.add(new Song("No Lie", "Dua Lipa", "Yabancı Pop", "3:30",
                "/com/musicplayer/nypmusicproje/ses/no-lie.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/no-lie.jpg"));

        allSongs.add(new Song("See You Again", "Wiz Khalifa ft. C.P", "Yabancı Pop", "3:49",
                "/com/musicplayer/nypmusicproje/ses/see-you-again.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/see-you-again.jpg"));

        allSongs.add(new Song("Affet", "Müslüm Gürses", "Arabesk", "4:15",
                "/com/musicplayer/nypmusicproje/ses/affet.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/affet.jpg"));

        allSongs.add(new Song("Hangimiz Sevmedik", "Müslüm Gürses", "Arabesk", "4:00",
                "/com/musicplayer/nypmusicproje/ses/hangimiz-sevmedik.mp3",
                "/com/musicplayer/nypmusicproje/images/albums/hangimiz-sevmedik.jpg"));
    }
    public void loadUserPlaylists(User user) {
        this.currentUser = user;
        playlists.clear();

        if (user.getPlaylists() != null && !user.getPlaylists().isEmpty()) {
            playlists.addAll(user.getPlaylists());
            System.out.println("✅ Kullanıcı playlist'leri yüklendi: " + playlists.size());
        } else {
            Playlist favorites = new Playlist("Favorilerim");
            playlists.add(favorites);
            user.addPlaylist(favorites);
            System.out.println("✅ Varsayılan playlist oluşturuldu");
        }

        AuthService.getInstance().updateCurrentUser();
    }

    public void saveUserPlaylists() {
        if (currentUser != null) {
            currentUser.getPlaylists().clear();
            currentUser.getPlaylists().addAll(playlists);
            AuthService.getInstance().updateCurrentUser();
            System.out.println("✅ Playlist'ler kaydedildi");
        }
    }

    public ObservableList<Song> getAllSongs() {
        return allSongs;
    }

    public void addSong(Song song) {
        allSongs.add(song);
    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public void createPlaylist(String name) {
        createPlaylist(name, true);
    }

    public void createPlaylist(String name, boolean checkEmpty) {
        if (checkEmpty && (name == null || name.trim().isEmpty())) {
            System.out.println("❌ Playlist adı boş olamaz!");
            return;
        }
        Playlist newPlaylist = new Playlist(name);
        playlists.add(newPlaylist);

        if (currentUser != null) {
            currentUser.addPlaylist(newPlaylist);
            saveUserPlaylists();
        }

        System.out.println("✅ Playlist oluşturuldu: " + name);
    }

    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist);

        if (currentUser != null) {
            currentUser.removePlaylist(playlist);
            saveUserPlaylists();
        }
    }

    public void play(Song song) {
        play(song, true);
    }

    public void play(Song song, boolean autoStart) {
        if (song == null || song.getFilePath() == null || song.getFilePath().isEmpty()) {
            System.err.println("❌ Şarkı dosyası bulunamadı!");
            return;
        }

        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            String path = song.getFilePath();
            java.net.URL resourceUrl = getClass().getResource(path);

            if (resourceUrl == null) {
                System.err.println("❌ Dosya bulunamadı: " + path);
                return;
            }

            Media media = new Media(resourceUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("🎵 Şarkı bitti");
                isPlaying = false;
            });

            mediaPlayer.setOnError(() -> {
                System.err.println("❌ MediaPlayer hatası: " + mediaPlayer.getError());
            });

            // ✅ Property'yi güncelle
            currentSongProperty.set(song);

            if (autoStart) {
                mediaPlayer.play();
                isPlaying = true;
                System.out.println("▶️ Şarkı çalınıyor: " + song.getTitle());
            } else {
                isPlaying = false;
                System.out.println("⏸️ Şarkı hazırlandı: " + song.getTitle());
            }

        } catch (Exception e) {
            System.err.println("❌ Şarkı çalınamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            System.out.println("⏸️ Müzik duraklatıldı");
        }
    }

    public void resume() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.play();
            isPlaying = true;
            System.out.println("▶️ Müzik devam ediyor");
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        isPlaying = false;

        // ✅ Property'yi sıfırla
        currentSongProperty.set(null);

        System.out.println("⏹️ Müzik durduruldu");
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public double getVolume() {
        return mediaPlayer != null ? mediaPlayer.getVolume() : 0.5;
    }

    public void seek(double seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(seconds));
        }
    }

    // ✅ Property getter - MainController için
    public ObjectProperty<Song> currentSongProperty() {
        return currentSongProperty;
    }

    // ✅ Normal getter - Property'den al
    public Song getCurrentSong() {
        return currentSongProperty.get();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}