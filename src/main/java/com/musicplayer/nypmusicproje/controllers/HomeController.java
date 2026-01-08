package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

// Ana sayfa arayüzünü yöneten controller sınıfı.
// Son çalınanlar ve önerilen şarkıların ekranda gösterilmesinden sorumludur.
public class HomeController {

    @FXML private Label welcomeLabel;
    @FXML private FlowPane recentSongsPane;
    @FXML private FlowPane recommendedPane;

    // Müzik oynatma ve şarkı verilerine erişim sağlayan servis
    private MusicPlayerService musicService;

    // Controller yüklendiğinde çalışan başlangıç metodu
    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();
        loadRecentSongs();
        loadRecommended();
    }

    // Son çalınan şarkıları temsil eden ilk 5 şarkıyı ekrana yükler
    private void loadRecentSongs() {
        recentSongsPane.getChildren().clear();

        for (int i = 0; i < 5 && i < musicService.getAllSongs().size(); i++) {
            Song song = musicService.getAllSongs().get(i);
            VBox songCard = createSongCard(song);
            recentSongsPane.getChildren().add(songCard);
        }
    }

    // Kullanıcıya önerilen şarkıları temsil eden sonraki 5 şarkıyı ekrana yükler
    private void loadRecommended() {
        recommendedPane.getChildren().clear();

        for (int i = 5; i < 10 && i < musicService.getAllSongs().size(); i++) {
            Song song = musicService.getAllSongs().get(i);
            VBox songCard = createSongCard(song);
            recommendedPane.getChildren().add(songCard);
        }
    }

    // Tek bir şarkı için görsel ve bilgileri içeren kart bileşeni oluşturur
    private VBox createSongCard(Song song) {
        VBox card = new VBox(8);
        card.getStyleClass().add("song-card");
        card.setPrefSize(150, 180);

        // Albüm kapağını göstermek için kullanılan alan
        VBox albumCoverContainer = new VBox();
        albumCoverContainer.getStyleClass().add("album-cover");
        albumCoverContainer.setPrefSize(150, 150);

        try {
            javafx.scene.image.ImageView albumCover = new javafx.scene.image.ImageView();
            javafx.scene.image.Image albumImage = new javafx.scene.image.Image(
                    getClass().getResourceAsStream(song.getAlbumArtPath())
            );
            albumCover.setImage(albumImage);
            albumCover.setFitWidth(150);
            albumCover.setFitHeight(150);
            albumCover.setPreserveRatio(false);
            albumCover.setSmooth(true);

            albumCoverContainer.getChildren().add(albumCover);
        } catch (Exception e) {
            // Albüm kapağı yüklenemezse hata alınmadan devam edilir
            System.err.println("Albüm kapağı yüklenemedi: " + song.getAlbumArtPath());
        }

        // Şarkı başlığı etiketi
        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("song-title");
        titleLabel.setMaxWidth(140);
        titleLabel.setWrapText(true);

        // Sanatçı bilgisi etiketi
        Label artistLabel = new Label(song.getArtist());
        artistLabel.getStyleClass().add("song-artist");
        artistLabel.setMaxWidth(140);

        card.getChildren().addAll(albumCoverContainer, titleLabel, artistLabel);

        // Kart tıklandığında ilgili şarkının çalınmasını sağlar
        card.setOnMouseClicked(e -> {
            musicService.play(song);
        });

        // Kullanıcı deneyimi için hover efekti
        card.setOnMouseEntered(e -> card.setStyle("-fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-cursor: default;"));

        return card;
    }
}
