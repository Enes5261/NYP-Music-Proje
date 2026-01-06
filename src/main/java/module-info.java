module com.musicplayer.nypmusicproje {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.musicplayer.nypmusicproje to javafx.fxml;
    exports com.musicplayer.nypmusicproje;
    exports com.musicplayer.nypmusicproje.controllers;
    opens com.musicplayer.nypmusicproje.controllers to javafx.fxml;
    exports com.musicplayer.nypmusicproje.models;
    opens com.musicplayer.nypmusicproje.models to javafx.fxml;
    exports com.musicplayer.nypmusicproje.services;
    opens com.musicplayer.nypmusicproje.services to javafx.fxml;
}