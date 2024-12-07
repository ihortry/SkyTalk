module griffith.skytalkpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
	requires json.simple;
    requires com.fasterxml.jackson.databind;
	requires java.dotenv;

    opens griffith.skytalkpro to javafx.fxml;
    exports griffith.skytalkpro;
}