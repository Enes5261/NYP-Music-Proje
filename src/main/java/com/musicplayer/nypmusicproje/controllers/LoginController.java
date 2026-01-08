package com.musicplayer.nypmusicproje.controllers;

import com.musicplayer.nypmusicproje.MelodixApplication;
import com.musicplayer.nypmusicproje.services.AuthService;
import com.musicplayer.nypmusicproje.services.MusicPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

// Kullanıcı giriş ve kayıt işlemlerini yöneten controller sınıfı.
// Kimlik doğrulama işlemleri AuthService üzerinden gerçekleştirilir.
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label errorLabel;

    // Kullanıcı kimlik doğrulama işlemlerinden sorumlu servis
    private AuthService authService;

    // Controller başlatıldığında servis hazırlanır
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        errorLabel.setVisible(false);
    }

    // Giriş butonuna basıldığında çalışan metot
    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Temel kullanıcı girdisi kontrolü
        if (username.isEmpty() || password.isEmpty()) {
            showError("Kullanıcı adı ve şifre boş olamaz!");
            return;
        }

        // Kimlik doğrulama işlemi
        if (authService.login(username, password)) {

            // Giriş yapan kullanıcıya ait oynatma listeleri yüklenir
            MusicPlayerService.getInstance().loadUserPlaylists(
                    authService.getCurrentUser()
            );

            openMainApp();
        } else {
            showError("Kullanıcı adı veya şifre hatalı!");
        }
    }

    // Kayıt ol butonuna basıldığında çalışan metot
    @FXML
    private void onRegisterClicked() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Kayıt işlemi için temel doğrulamalar
        if (username.isEmpty() || password.isEmpty()) {
            showError("Kullanıcı adı ve şifre boş olamaz!");
            return;
        }

        if (username.length() < 3) {
            showError("Kullanıcı adı en az 3 karakter olmalı!");
            return;
        }

        if (password.length() < 4) {
            showError("Şifre en az 4 karakter olmalı!");
            return;
        }

        // Kullanıcı kayıt işlemi
        if (authService.register(username, password)) {

            // Yeni kullanıcıya ait oynatma listeleri hazırlanır
            MusicPlayerService.getInstance().loadUserPlaylists(
                    authService.getCurrentUser()
            );

            showSuccess("Kayıt başarılı! Hoş geldin " + username + "!");

            // Kısa gecikme sonrası ana ekrana geçiş yapılır
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(this::openMainApp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            showError("Bu kullanıcı adı zaten kullanılıyor!");
        }
    }

    // Ana uygulama ekranını yükler
    private void openMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MelodixApplication.class.getResource("main-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 1200, 700);

            String cssPath = MelodixApplication.class.getResource("styles.css") != null
                    ? MelodixApplication.class.getResource("styles.css").toExternalForm()
                    : null;

            if (cssPath != null) {
                scene.getStylesheets().add(cssPath);
            }

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Melodix - " + authService.getCurrentUser().getUsername());

        } catch (IOException e) {
            e.printStackTrace();
            showError("Ana ekran açılamadı!");
        }
    }

    // Hata durumlarında kullanıcıya geri bildirim verir
    private void showError(String message) {
        errorLabel.setText("❌ " + message);
        errorLabel.setStyle("-fx-text-fill: #ff4444;");
        errorLabel.setVisible(true);
    }

    // Başarılı işlemler için bilgilendirici mesaj gösterir
    private void showSuccess(String message) {
        errorLabel.setText("✅ " + message);
        errorLabel.setStyle("-fx-text-fill: #44ff44;");
        errorLabel.setVisible(true);
    }
}
