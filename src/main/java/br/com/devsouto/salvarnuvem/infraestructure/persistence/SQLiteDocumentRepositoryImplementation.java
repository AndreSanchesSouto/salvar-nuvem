package br.com.devsouto.salvarnuvem.infraestructure.persistence;

import br.com.devsouto.salvarnuvem.infraestructure.configuration.DatabaseManager;
import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public Optional<Document> findByName(String folderName) {
        return Optional.empty();
    }

    @Override
    public Optional<Document> findById(String folderId) {
        return Optional.empty();
    }
}
