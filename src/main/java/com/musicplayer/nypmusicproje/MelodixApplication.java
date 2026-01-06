package com.musicplayer.nypmusicproje;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MelodixApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MelodixApplication.class.getResource("main-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        // CSS dosyasını yükle
        String cssPath = getClass().getResource("styles.css") != null
                ? getClass().getResource("styles.css").toExternalForm()
                : null;

        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        } else {
            System.out.println("UYARI: styles.css dosyası bulunamadı!");
        }

        stage.setTitle("Melodix - Modern Music Player");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}