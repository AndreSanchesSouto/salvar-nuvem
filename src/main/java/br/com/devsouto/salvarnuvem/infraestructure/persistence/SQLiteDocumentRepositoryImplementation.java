package br.com.devsouto.salvarnuvem.infraestructure.persistence;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;
import br.com.devsouto.salvarnuvem.infraestructure.configuration.DatabaseManager;
import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteDocumentRepositoryImplementation implements DocumentRepository {

    @Override
    public DocumentEntity salvar(Document document) {
        String sql = """
                INSERT INTO Documents(
                    name,
                    parentPath,
                    localPath,
                    remoteId,
                    remoteParentId,
                    type
                ) VALUES (
                    ?, ?, ?, ?, ?, ?
                );
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String parent = document.getLocalParentPath() != null ? document.getLocalParentPath().toString() : null;

            statement.setString(1, document.getName());
            statement.setString(2, parent);
            statement.setString(3, document.getLocalPath().toString());
            statement.setString(4, document.getRemoteId());
            statement.setString(5, document.getRemoteParentId());
            statement.setString(6, document.getType().toString());

            statement.execute();

            return new DocumentEntity(document);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DocumentEntity> findByName(String folderName) {
        return Optional.empty();
    }

    @Override
    public Optional<DocumentEntity> findById(String folderId) {
        return Optional.empty();
    }

    @Override
    public List<DocumentEntity> findRootFolders() {
        String sql = """
                SELECT 
                    *
                FROM Documents
                WHERE
                    type = 'FOLDER'
                        AND parentPath IS NULL;
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<DocumentEntity> documents = new ArrayList<DocumentEntity>();

            while (resultSet.next()) {
                documents.add(
                        this.createDocumentEntity(resultSet)
                );
            }

            return documents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DocumentEntity> findFolders() {
        String sql = """
            SELECT 
                *
            FROM Documents
            WHERE
                type = 'FOLDER'
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<DocumentEntity> documents = new ArrayList<DocumentEntity>();

            while (resultSet.next()) {
                documents.add(
                        this.createDocumentEntity(resultSet)
                );
            }

            return documents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DocumentEntity createDocumentEntity(ResultSet resultSet) throws SQLException {
        return new DocumentEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                this.toPath(resultSet.getString("parentPath")),
                this.toPath(resultSet.getString("localPath")),
                resultSet.getString("remoteId"),
                resultSet.getString("remoteParentId"),
                DocumentType.valueOf(resultSet.getString("type"))
        );
    }

    private Path toPath(String value) {
        return value != null ? Path.of(value) : null;
    }
}
