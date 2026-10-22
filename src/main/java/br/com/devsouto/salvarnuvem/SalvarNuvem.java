package br.com.devsouto.salvarnuvem;

import br.com.devsouto.salvarnuvem.domain.service.DriveService;
import br.com.devsouto.salvarnuvem.infraestructure.configuration.DatabaseManager;
import br.com.devsouto.salvarnuvem.controller.MainController;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.SQLiteDocumentRepositoryImplementation;
import br.com.devsouto.salvarnuvem.domain.service.DocumentService;
import br.com.devsouto.salvarnuvem.domain.service.MainService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SalvarNuvem extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseManager.initialize();

        DocumentRepository repository = new SQLiteDocumentRepositoryImplementation();

        DocumentService documentService = new DocumentService(repository);
        DriveService driveService = new DriveService();

        MainService mainService = new MainService(documentService, driveService);

        this.renderPanel(stage, mainService);
    }

    private void renderPanel(Stage stage, MainService mainService) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SalvarNuvem.class.getResource("MainView.fxml"));

        fxmlLoader.setControllerFactory(controllerType -> {
            if (controllerType == MainController.class) {
                return new MainController(mainService);
            }

            try {
                return controllerType.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        });


        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Salvar nuvem");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
