package br.com.devsouto.salvarnuvem.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.io.IOException;

public class DatabaseManager {

    private static final String APP_DIRECTORY = "salvar-nuvem";
    private static final String DATABASE_NAME = "salvar-nuvem.db";

    private static Path databasePath;

    private DatabaseManager() { }

    public static void initialize() throws SQLException, IOException {

        try (Connection connection = getConnection()) {

        } catch(SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static Connection getConnection() throws SQLException {
        // 6. Garantir que o banco foi inicializado.
        // 7. Abrir e retornar uma nova conexão.
        return DriverManager.getConnection(
                "jdbc:sqlite:" + databasePath
        );
    }
}






