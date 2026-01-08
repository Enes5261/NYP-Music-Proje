package com.musicplayer.nypmusicproje;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MelodixApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Login ekranıyla başla
        FXMLLoader fxmlLoader = new FXMLLoader(
                MelodixApplication.class.getResource("login-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 500, 700);

        // CSS dosyasını yükle
        URL cssURL = getClass().getResource("styles.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
            System.out.println("✅ CSS yüklendi: " + cssURL);
        } else {
            System.err.println("❌ HATA: styles.css bulunamadı!");
        }

        // Window icon ekle
        try {
            Image icon = new Image(
                    getClass().getResourceAsStream("images/melodix-logo.png")
            );
            stage.getIcons().add(icon);
            System.out.println("✅ Window icon yüklendi");
        } catch (Exception e) {
            System.err.println("❌ Window icon yüklenemedi: " + e.getMessage());
        }

        stage.setTitle("Melodix - Giriş");
        stage.setScene(scene);
        stage.setResizable(true);  // Yeniden boyutlandırılabilir yap
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}