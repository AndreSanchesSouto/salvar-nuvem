module br.com.devsouto.salvarnuvem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.services.drive;
    requires jdk.httpserver;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires org.jspecify;
    requires java.sql;

    opens br.com.devsouto.salvarnuvem to javafx.fxml;
    opens br.com.devsouto.salvarnuvem.controller to javafx.fxml;

    exports br.com.devsouto.salvarnuvem;
    exports br.com.devsouto.salvarnuvem.domain.model;
    exports br.com.devsouto.salvarnuvem.domain.enums;
    opens br.com.devsouto.salvarnuvem.domain.model to javafx.fxml;
}