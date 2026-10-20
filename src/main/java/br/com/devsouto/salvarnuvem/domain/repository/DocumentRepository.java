package br.com.devsouto.salvarnuvem.domain.repository;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.DocumentEntity;

import java.util.Optional;

public interface DocumentRepository {
    DocumentEntity salvar(Document folder);
    Optional<Document> findByName(String folderName);
    Optional<Document> findById(String folderId);
}
