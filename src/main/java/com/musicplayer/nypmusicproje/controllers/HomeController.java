package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class HomeController {

    @FXML private Label welcomeLabel;
    @FXML private FlowPane recentSongsPane;
    @FXML private FlowPane recommendedPane;

    private MusicPlayerService musicService;

    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();
        loadRecentSongs();
        loadRecommended();
    }

    private void loadRecentSongs() {
        // Son çalınan şarkılar (ilk 5 şarkı)
        for (int i = 0; i < Math.min(5, musicService.getAllSongs().size()); i++) {
            Song song = musicService.getAllSongs().get(i);
            VBox songCard = createSongCard(song);
            recentSongsPane.getChildren().add(songCard);
        }
    }

    private void loadRecommended() {
        // Önerilen şarkılar (son 5 şarkı)
        int start = Math.max(0, musicService.getAllSongs().size() - 5);
        for (int i = start; i < musicService.getAllSongs().size(); i++) {
            Song song = musicService.getAllSongs().get(i);
            VBox songCard = createSongCard(song);
            recommendedPane.getChildren().add(songCard);
        }
    }

    private VBox createSongCard(Song song) {
        VBox card = new VBox(8);
        card.getStyleClass().add("song-card");
        card.setPrefSize(150, 180);

        // Albüm kapağı placeholder
        VBox albumCover = new VBox();
        albumCover.getStyleClass().add("album-cover");
        albumCover.setPrefSize(150, 150);

        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("song-title");
        titleLabel.setMaxWidth(140);

        Label artistLabel = new Label(song.getArtist());
        artistLabel.getStyleClass().add("song-artist");
        artistLabel.setMaxWidth(140);

        card.getChildren().addAll(albumCover, titleLabel, artistLabel);

        // Tıklama eventi
        card.setOnMouseClicked(e -> {
            musicService.play(song);
            System.out.println("Çalınıyor: " + song.getTitle());
        });

        return card;
    }
}