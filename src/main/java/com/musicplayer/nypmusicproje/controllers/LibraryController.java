package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// Müzik kütüphanesi ekranını yöneten controller sınıfı.
// Şarkı listeleme, arama ve oynatma listesi işlemlerini kontrol eder.
public class LibraryController {

    @FXML private TableView<Song> songsTable;
    @FXML private TableColumn<Song, String> titleColumn;
    @FXML private TableColumn<Song, String> artistColumn;
    @FXML private TableColumn<Song, String> albumColumn;
    @FXML private TableColumn<Song, String> durationColumn;
    @FXML private TextField searchField;

    // Şarkı ve oynatma listesi işlemlerini yöneten servis
    private MusicPlayerService musicService;

    // Controller yüklendiğinde tablo ve olaylar hazırlanır
    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();

        // Tablo kolonlarını model sınıfındaki alanlara bağlar
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Tüm şarkıları tabloya yükler
        songsTable.setItems(musicService.getAllSongs());

        // Çift tıklama ile seçili şarkının çalınmasını sağlar
        songsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Song selectedSong = songsTable.getSelectionModel().getSelectedItem();
                if (selectedSong != null) {
                    musicService.play(selectedSong);
                }
            }
        });

        // Sağ tık menüsünü hazırlar
        setupContextMenu();
    }

    // Tablo için sağ tık (context menu) işlemlerini oluşturur
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem playItem = new MenuItem("Çal");
        playItem.setOnAction(e -> {
            Song selected = songsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                musicService.play(selected);
            }
        });

        Menu addToPlaylistMenu = new Menu("Oynatma Listesine Ekle");
        updatePlaylistMenu(addToPlaylistMenu);

        contextMenu.getItems().addAll(playItem, new SeparatorMenuItem(), addToPlaylistMenu);
        songsTable.setContextMenu(contextMenu);
    }

    // Mevcut oynatma listelerine göre menüyü dinamik olarak günceller
    private void updatePlaylistMenu(Menu playlistMenu) {
        playlistMenu.getItems().clear();

        for (Playlist playlist : musicService.getPlaylists()) {
            MenuItem item = new MenuItem(playlist.getName());
            item.setOnAction(e -> {
                Song selected = songsTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    addSongToPlaylist(selected, playlist);
                }
            });
            playlistMenu.getItems().add(item);
        }

        // Oynatma listesi yoksa bilgilendirici mesaj gösterir
        if (playlistMenu.getItems().isEmpty()) {
            MenuItem empty = new MenuItem("Oynatma listesi yok");
            empty.setDisable(true);
            playlistMenu.getItems().add(empty);
        }
    }

    // Şarkının aynı oynatma listesine tekrar eklenmesini engeller
    private void addSongToPlaylist(Song song, Playlist playlist) {
        if (playlist.getSongs().contains(song)) {
            showWarningAlert(
                    "Müzik Zaten Mevcut",
                    "\"" + song.getTitle() + "\" şarkısı \"" + playlist.getName() + "\" listesinde zaten var."
            );
        } else {
            playlist.addSong(song);

            // Yapılan değişiklikleri kalıcı hale getirir
            musicService.saveUserPlaylists();

            showAlert("Başarılı", "Şarkı oynatma listesine eklendi.");
        }
    }

    // Arama alanına göre tabloyu dinamik olarak filtreler
    @FXML
    private void onSearchKeyReleased() {
        String searchText = searchField.getText().toLowerCase();

        if (searchText.isEmpty()) {
            songsTable.setItems(musicService.getAllSongs());
        } else {
            songsTable.setItems(
                    musicService.getAllSongs().filtered(song ->
                            song.getTitle().toLowerCase().contains(searchText) ||
                                    song.getArtist().toLowerCase().contains(searchText) ||
                                    song.getAlbum().toLowerCase().contains(searchText)
                    )
            );
        }
    }

    // Bilgilendirme amaçlı mesaj penceresi gösterir
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Kullanıcıyı uyarmak için uyarı mesajı gösterir
    private void showWarningAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText("Uyarı");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
