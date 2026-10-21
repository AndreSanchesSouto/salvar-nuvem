package br.com.devsouto.salvarnuvem.infraestructure.persistence;

import br.com.devsouto.salvarnuvem.infraestructure.configuration.DatabaseManager;
import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class SQLiteDocumentRepositoryImplementation implements DocumentRepository {

    @Override
    public DocumentEntity salvar(Document document) {
        String sql = """
                INSERT INTO Documents(
                
                ) VALUES (
                
                );
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Path localPath = Path.of("");
            return new DocumentEntity(
                    "",
                    "",
                    localPath,
                    localPath,
                    ""
            );
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
