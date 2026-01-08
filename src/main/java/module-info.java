module com.musicplayer.nypmusicproje {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;
    requires javafx.media;  // GSON EKLE

    opens com.musicplayer.nypmusicproje to javafx.fxml;
    opens com.musicplayer.nypmusicproje.controllers to javafx.fxml;
    opens com.musicplayer.nypmusicproje.models to com.google.gson;  // GSON İÇİN AÇ

    exports com.musicplayer.nypmusicproje;
    exports com.musicplayer.nypmusicproje.controllers;
    exports com.musicplayer.nypmusicproje.models;
}