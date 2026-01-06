package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.MelodixApplication;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private Button homeButton;
    @FXML private Button libraryButton;
    @FXML private Button playlistsButton;
    @FXML private StackPane contentArea;
    @FXML private Label currentSongLabel;
    @FXML private Label currentArtistLabel;
    @FXML private Button playPauseButton;

    private MusicPlayerService musicService;

    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();
        loadView("home-view.fxml");
        updateNowPlaying();
    }

    @FXML
    private void onHomeClicked() {
        setActiveButton(homeButton);
        loadView("home-view.fxml");
    }

    @FXML
    private void onLibraryClicked() {
        setActiveButton(libraryButton);
        loadView("library-view.fxml");
    }

    @FXML
    private void onPlaylistsClicked() {
        setActiveButton(playlistsButton);
        loadView("playlists-view.fxml");
    }

    @FXML
    private void onPlayPauseClicked() {
        if (musicService.getCurrentSong() == null) {
            // İlk şarkıyı çal
            if (!musicService.getAllSongs().isEmpty()) {
                musicService.play(musicService.getAllSongs().get(0));
            }
        } else {
            if (musicService.isPlaying()) {
                musicService.pause();
                playPauseButton.setText("▶");
            } else {
                musicService.play(musicService.getCurrentSong());
                playPauseButton.setText("⏸");
            }
        }
        updateNowPlaying();
    }

    @FXML
    private void onPreviousClicked() {
        // Önceki şarkı mantığı
        System.out.println("Önceki şarkı");
    }

    @FXML
    private void onNextClicked() {
        // Sonraki şarkı mantığı
        System.out.println("Sonraki şarkı");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MelodixApplication.class.getResource(fxmlFile)
            );
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActiveButton(Button activeButton) {
        homeButton.getStyleClass().remove("active");
        libraryButton.getStyleClass().remove("active");
        playlistsButton.getStyleClass().remove("active");
        activeButton.getStyleClass().add("active");
    }

    private void updateNowPlaying() {
        if (musicService.getCurrentSong() != null) {
            currentSongLabel.setText(musicService.getCurrentSong().getTitle());
            currentArtistLabel.setText(musicService.getCurrentSong().getArtist());
            playPauseButton.setText(musicService.isPlaying() ? "⏸" : "▶");
        } else {
            currentSongLabel.setText("Şarkı seçilmedi");
            currentArtistLabel.setText("");
            playPauseButton.setText("▶");
        }
    }

    public void refreshNowPlaying() {
        updateNowPlaying();
    }
}