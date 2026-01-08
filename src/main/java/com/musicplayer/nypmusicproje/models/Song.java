package com.musicplayer.nypmusicproje.models;

/**
 * Song sınıfı, uygulamadaki bir müzik parçasını temsil eder.
 * Model katmanında yer alır ve şarkıya ait temel bilgileri içerir.
 */
public class Song {

    private String title;
    private String artist;
    private String album;
    private String duration;
    private String filePath;
    private String albumArtPath; // Albüm kapağı yolu

    // Constructor - Albüm kapağı olmadan (geriye dönük uyumluluk)
    public Song(String title, String artist, String album, String duration, String filePath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
        this.albumArtPath =
                "/com/musicplayer/nypmusicproje/images/albums/default-album.png";
    }

    // Constructor - Albüm kapağı ile (OVERLOADING)
    public Song(String title, String artist, String album,
                String duration, String filePath, String albumArtPath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
        this.albumArtPath = albumArtPath;
    }

    // Getter metodları
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public String getDuration() { return duration; }
    public String getFilePath() { return filePath; }
    public String getAlbumArtPath() { return albumArtPath; }

    // Setter metodları
    public void setTitle(String title) { this.title = title; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setAlbum(String album) { this.album = album; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    // Şarkının ListView / TableView'da nasıl görüneceğini belirler
    @Override
    public String toString() {
        return title + " - " + artist;
    }

    // Şarkı nesnelerinin içerik bazlı karşılaştırılması (OVERRIDING)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Song song = (Song) obj;
        return title.equals(song.title) &&
                artist.equals(song.artist) &&
                album.equals(song.album);
    }

    // equals metodu ile uyumlu hashCode
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + album.hashCode();
        return result;
    }
}
