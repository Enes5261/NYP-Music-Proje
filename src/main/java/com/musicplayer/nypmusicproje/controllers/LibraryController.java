package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class LibraryController {

    @FXML private TableView<Song> songsTable;
    @FXML private TableColumn<Song, String> titleColumn;
    @FXML private TableColumn<Song, String> artistColumn;
    @FXML private TableColumn<Song, String> albumColumn;
    @FXML private TableColumn<Song, String> durationColumn;
    @FXML private TextField searchField;

    private MusicPlayerService musicService;

    @FXML
    public void initialize() {
        musicService = MusicPlayerService.getInstance();

        // Tablo kolonlarını ayarla
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Şarkıları yükle
        songsTable.setItems(musicService.getAllSongs());

        // Çift tıklama ile çal
        songsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Song selectedSong = songsTable.getSelectionModel().getSelectedItem();
                if (selectedSong != null) {
                    musicService.play(selectedSong);
                }
            }
        });

        // Sağ tık menüsü
        setupContextMenu();
    }

    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem playItem = new MenuItem("Çal");
        playItem.setOnAction(e -> {
            Song selected = songsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                musicService.play(selected);
            }
        });

        MenuItem addToPlaylistItem = new MenuItem("Oynatma Listesine Ekle");
        addToPlaylistItem.setOnAction(e -> showAddToPlaylistDialog());

        Menu addToPlaylistMenu = new Menu("Oynatma Listesine Ekle");
        updatePlaylistMenu(addToPlaylistMenu);

        contextMenu.getItems().addAll(playItem, new SeparatorMenuItem(), addToPlaylistMenu);
        songsTable.setContextMenu(contextMenu);
    }

    private void updatePlaylistMenu(Menu playlistMenu) {
        playlistMenu.getItems().clear();

        for (Playlist playlist : musicService.getPlaylists()) {
            MenuItem item = new MenuItem(playlist.getName());
            item.setOnAction(e -> {
                Song selected = songsTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    playlist.addSong(selected);
                    showAlert("Başarılı", "Şarkı oynatma listesine eklendi!");
                }
            });
            playlistMenu.getItems().add(item);
        }

        if (playlistMenu.getItems().isEmpty()) {
            MenuItem empty = new MenuItem("Oynatma listesi yok");
            empty.setDisable(true);
            playlistMenu.getItems().add(empty);
        }
    }

    private void showAddToPlaylistDialog() {
        Song selected = songsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ChoiceDialog<Playlist> dialog = new ChoiceDialog<>(
                null,
                musicService.getPlaylists()
        );
        dialog.setTitle("Oynatma Listesine Ekle");
        dialog.setHeaderText("Şarkıyı eklemek istediğiniz listeyi seçin");
        dialog.setContentText("Oynatma Listesi:");

        Optional<Playlist> result = dialog.showAndWait();
        result.ifPresent(playlist -> {
            playlist.addSong(selected);
            showAlert("Başarılı", "Şarkı eklendi!");
        });
    }

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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}