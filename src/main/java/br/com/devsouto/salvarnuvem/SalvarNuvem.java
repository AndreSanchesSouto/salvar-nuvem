package br.com.devsouto.salvarnuvem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SalvarNuvem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SalvarNuvem.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Salvar nuvem");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
