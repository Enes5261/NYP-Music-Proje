package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.MelodixApplication;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

import java.io.IOException;

// Uygulamanın ana ekranını ve sayfa geçişlerini yöneten controller sınıfı.
// Oynatma kontrolleri ve aktif ekran yönetimi bu sınıfta gerçekleştirilir.
public class MainController {

    @FXML private Button homeButton;
    @FXML private Button libraryButton;
    @FXML private Button playlistsButton;
    @FXML private StackPane contentArea;
    @FXML private Label currentSongLabel;
    @FXML private Label currentArtistLabel;
    @FXML private Button playPauseButton;
    @FXML private Slider volumeSlider;

    // Müzik oynatma işlemlerini yöneten servis
    private MusicPlayerService musicService;

    // Controller yüklendiğinde varsayılan ekran ve dinleyiciler hazırlanır
    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();
        loadView("home-view.fxml");

        // Çalan şarkı değiştiğinde arayüzü otomatik günceller
        musicService.currentSongProperty().addListener((obs, oldSong, newSong) -> {
            updateNowPlaying();
        });

        setupVolumeControl();
        updateNowPlaying();
    }

    // Ses seviyesi kontrolü için slider yapılandırması
    private void setupVolumeControl() {
        if (volumeSlider != null) {

            // Başlangıç ses seviyesi ayarlanır
            volumeSlider.setValue(musicService.getVolume() * 100);

            // Slider hareketine göre ses seviyesi güncellenir
            volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                double volume = newVal.doubleValue() / 100.0;
                musicService.setVolume(volume);
            });
        }
    }

    // Ana sayfa ekranını yükler
    @FXML
    private void onHomeClicked() {
        setActiveButton(homeButton);
        loadView("home-view.fxml");
    }

    // Kütüphane ekranını yükler
    @FXML
    private void onLibraryClicked() {
        setActiveButton(libraryButton);
        loadView("library-view.fxml");
    }

    // Oynatma listeleri ekranını yükler
    @FXML
    private void onPlaylistsClicked() {
        setActiveButton(playlistsButton);
        loadView("playlists-view.fxml");
    }

    // Oynat / duraklat işlemini kontrol eder
    @FXML
    private void onPlayPauseClicked() {
        if (musicService.getCurrentSong() == null) {
            if (!musicService.getAllSongs().isEmpty()) {
                musicService.play(musicService.getAllSongs().get(0));
            }
        } else {
            if (musicService.isPlaying()) {
                musicService.pause();
                playPauseButton.setText("▶");
            } else {
                musicService.resume();
                playPauseButton.setText("⏸");
            }
        }
        updateNowPlaying();
    }

    // Önceki şarkı işlemi (geliştirmeye açık)
    @FXML
    private void onPreviousClicked() {
        System.out.println("Önceki şarkı");
    }

    // Sonraki şarkı işlemi (geliştirmeye açık)
    @FXML
    private void onNextClicked() {
        System.out.println("Sonraki şarkı");
    }

    // İlgili FXML dosyasını içerik alanına yükler
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

    // Aktif olan menü butonunu vurgular
    private void setActiveButton(Button activeButton) {
        homeButton.getStyleClass().remove("active");
        libraryButton.getStyleClass().remove("active");
        playlistsButton.getStyleClass().remove("active");
        activeButton.getStyleClass().add("active");
    }

    // Çalan şarkı bilgilerini alt panelde günceller
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

    // Diğer controller'lar tarafından tetiklenebilen yenileme metodu
    public void refreshNowPlaying() {
        updateNowPlaying();
    }
}
