package br.com.devsouto.salvarnuvem.controller;

import br.com.devsouto.salvarnuvem.dto.DocumentDTO;
import br.com.devsouto.salvarnuvem.service.DocumentService;
import br.com.devsouto.salvarnuvem.service.MainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class MainController {

    @FXML
    private ListView<DocumentDTO> userDirectoryList;

    @FXML
    private Button btnAddPathSelector;

    // Lista observável: qualquer alteração aqui atualiza a tela automaticamente
    private final ObservableList<DocumentDTO> pathItems = FXCollections.observableArrayList();

    private final MainService servise;

    public MainController(MainService mainService) {
        this.servise = mainService;
    }

    @FXML
    public void initialize() {
        userDirectoryList.setItems(pathItems);

        // Define a fábrica de células: ensina o JavaFX como desenhar cada item da lista
        userDirectoryList.setCellFactory(param -> new ListCell<DocumentDTO>() {
            private final HBox layout = new HBox(10.0);
            private final TextField txtField = new TextField();
            private final TextField txtName = new TextField();
            private final Button btnProcurar = new Button("Procurar...");
            private final Button btnRemover = new Button("-");

            {
                txtName.setPrefHeight(25.0);
                txtName.setPrefWidth(100.0);
                txtName.setPromptText("Nome da pasta...");

                txtField.setPrefHeight(25.0);
                txtField.setPrefWidth(300.0);
                txtField.setPromptText("Selecione uma pasta...");
                txtField.setEditable(false);

                btnProcurar.setCursor(Cursor.HAND);
                btnRemover.setCursor(Cursor.HAND);

                // Ação do botão de procurar desta linha específica
                btnProcurar.setOnAction(event -> {
                    DocumentDTO item = getItem();
                    if (item != null) {
                        DirectoryChooser directoryChooser = new DirectoryChooser();
                        Window owner = btnProcurar.getScene().getWindow();
                        Path selectedFile = directoryChooser.showDialog(owner).toPath();
                        final DocumentDTO folder = new DocumentDTO(selectedFile);
                        txtField.setText(folder.localPath().toAbsolutePath().toString());
                        servise.synchronize(folder.localPath(), txtName.getCharacters().toString());
                    }
                });

                // Ação do botão de remover esta linha específica
                btnRemover.setOnAction(event -> {
                    DocumentDTO item = getItem();
                    if (item != null) {
                        pathItems.remove(item); // Remove do dado, o ListView some com a linha sozinho
                    }
                });

                layout.getChildren().addAll(txtName, txtField, btnProcurar, btnRemover);
            }

            @Override
            protected void updateItem(DocumentDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    if (item.localPath() != null) {
                        txtField.setText(item.localPath().toAbsolutePath().toString());
                    } else {
                        txtField.setText("");
                    }
                    setGraphic(layout);
                }
            }
        });

        // Adiciona um item inicial padrão ao abrir a tela, se desejar
        if (pathItems.isEmpty())
            pathItems.add(new DocumentDTO(null));
    }

    @FXML
    void adicionarNovoCampo(ActionEvent event) {
        // Apenas adicionamos um novo dado na lista. O ListView cuida do resto!
        pathItems.add(new DocumentDTO(null));
    }

    @FXML
    void abrirSettings(ActionEvent event) throws IOException {
        Stage settingsStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/devsouto/salvarnuvem/SettingsView.fxml")));

        // Pega a janela atual a partir do botão clicado
        Window owner = ((Button) event.getSource()).getScene().getWindow();
        settingsStage.initOwner(owner);

        // Opcional: Torna modal (trava a principal enquanto a de settings estiver aberta)
        settingsStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(root);
        settingsStage.setScene(scene);
        settingsStage.setTitle("Configurações");
        settingsStage.show();
    }
}