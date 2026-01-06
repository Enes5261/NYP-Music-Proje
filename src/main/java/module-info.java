module com.musicplayer.nypmusicproje {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.musicplayer.nypmusicproje to javafx.fxml;
    exports com.musicplayer.nypmusicproje;
}