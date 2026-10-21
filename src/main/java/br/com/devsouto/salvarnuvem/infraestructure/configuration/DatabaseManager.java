package br.com.devsouto.salvarnuvem.infraestructure.configuration;

import java.sql.*;
import java.io.IOException;

public class DatabaseManager {

    private static final String JDBC_SQLITE = "jdbc:sqlite:";
    private static final String DATABASE_NAME = "salvar-nuvem.db";

    private DatabaseManager() { }

    public static void initialize() throws SQLException, IOException {

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {

            createDatabase(statement);

        } catch(SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                JDBC_SQLITE + DATABASE_NAME
        );
    }

    private static void createDatabase(Statement statement) throws SQLException {
        final String sqlUserDirectory = """
                CREATE TABLE IF NOT EXISTS Documents (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    parentPath TEXT NULL,
                    localPath TEXT NOT NULL,
                    remoteId TEXT NOT NULL,
                    type TEXT NOT NULL
                );
                """;
        statement.execute(sqlUserDirectory);
    }
}






