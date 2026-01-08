package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class PlaylistsController {

    // FXML bileşenleri
    @FXML private ListView<Playlist> playlistListView;
    @FXML private ListView<Song> playlistSongsView;
    @FXML private Label playlistNameLabel;
    @FXML private Label songCountLabel;
    @FXML private Button createPlaylistButton;
    @FXML private Button deletePlaylistButton;
    @FXML private Button removeFromPlaylistButton;

    private MusicPlayerService musicService;
    private Playlist selectedPlaylist;

    @FXML
    public void initialize() {
        // Singleton müzik servisini al
        musicService = MusicPlayerService.getInstance();

        // Oynatma listelerini ListView'e yükle
        playlistListView.setItems(musicService.getPlaylists());

        // Liste seçimi değiştiğinde detayları güncelle
        playlistListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        loadPlaylistDetails(newVal);
                    }
                }
        );

        // Şarkıya çift tıklanınca çalmaya başla
        playlistSongsView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Song selected = playlistSongsView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    musicService.play(selected);
                }
            }
        });

        // Uygulama açıldığında ilk oynatma listesini seç
        if (!musicService.getPlaylists().isEmpty()) {
            playlistListView.getSelectionModel().select(0);
        }
    }

    // Seçilen oynatma listesinin bilgilerini ekrana yükler
    private void loadPlaylistDetails(Playlist playlist) {
        selectedPlaylist = playlist;
        playlistNameLabel.setText(playlist.getName());
        songCountLabel.setText(playlist.getSongCount() + " şarkı");
        playlistSongsView.setItems(playlist.getSongs());
    }

    // Yeni oynatma listesi oluşturur
    @FXML
    private void onCreatePlaylistClicked() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Yeni Oynatma Listesi");
        dialog.setHeaderText("Yeni oynatma listesi oluştur");
        dialog.setContentText("Liste adı:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                musicService.createPlaylist(name);
                playlistListView.getSelectionModel().selectLast();
            }
        });
    }

    // Seçilen oynatma listesini siler
    @FXML
    private void onDeletePlaylistClicked() {
        Playlist selected = playlistListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silinecek listeyi seçin!");
            return;
        }

        // Silme işlemi için kullanıcıdan onay al
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Silme Onayı");
        confirm.setHeaderText("'" + selected.getName() + "' listesini silmek istediğinize emin misiniz?");
        confirm.setContentText("Bu işlem geri alınamaz.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            musicService.deletePlaylist(selected);
            playlistNameLabel.setText("Oynatma Listesi");
            songCountLabel.setText("0 şarkı");
            playlistSongsView.getItems().clear();
        }
    }

    // Seçilen şarkıyı oynatma listesinden kaldırır
    @FXML
    private void onRemoveFromPlaylistClicked() {
        if (selectedPlaylist == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen önce bir liste seçin!");
            return;
        }

        Song selected = playlistSongsView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen kaldırılacak şarkıyı seçin!");
            return;
        }

        selectedPlaylist.removeSong(selected);
        songCountLabel.setText(selectedPlaylist.getSongCount() + " şarkı");

        // Kullanıcı oynatma listelerini kaydet
        musicService.saveUserPlaylists();

        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Şarkı listeden kaldırıldı!");
    }

    // Oynatma listesindeki ilk şarkıyı çalmaya başlar
    @FXML
    private void onPlayPlaylistClicked() {
        if (selectedPlaylist != null && !selectedPlaylist.getSongs().isEmpty()) {
            musicService.play(selectedPlaylist.getSongs().get(0));
            showAlert(Alert.AlertType.INFORMATION, "Çalınıyor",
                    "Oynatma listesi çalmaya başladı!");
        }
    }

    // Oynatma listesinin adını değiştirir
    @FXML
    private void onRenamePlaylistClicked() {
        if (selectedPlaylist == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen yeniden adlandırılacak listeyi seçin!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selectedPlaylist.getName());
        dialog.setTitle("Listeyi Yeniden Adlandır");
        dialog.setHeaderText("Yeni isim girin");
        dialog.setContentText("Liste adı:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                selectedPlaylist.setName(name);
                playlistListView.refresh();
                playlistNameLabel.setText(name);

                musicService.saveUserPlaylists();
            }
        });
    }

    // Genel uyarı ve bilgilendirme penceresi
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
