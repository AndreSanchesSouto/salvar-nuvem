module br.com.devsouto.salvarnuvem {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.com.devsouto.salvarnuvem to javafx.fxml;
    opens br.com.devsouto.salvarnuvem.controller to javafx.fxml;

    exports br.com.devsouto.salvarnuvem;
    exports br.com.devsouto.salvarnuvem.model;
    opens br.com.devsouto.salvarnuvem.model to javafx.fxml;
}