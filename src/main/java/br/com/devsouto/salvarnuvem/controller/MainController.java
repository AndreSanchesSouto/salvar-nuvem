package br.com.devsouto.salvarnuvem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MainController {
    @FXML Button btnPathSelector;

    @FXML public void warning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText("clicou");
        alert.setContentText("Clique confirmado!");

        alert.showAndWait();
    }
}
