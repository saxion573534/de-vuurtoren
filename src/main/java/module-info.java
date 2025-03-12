module org.saxion.devuurtoren {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens org.saxion.devuurtoren to javafx.fxml;
    exports org.saxion.devuurtoren;
}