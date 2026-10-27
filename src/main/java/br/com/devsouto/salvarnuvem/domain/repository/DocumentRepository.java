package br.com.devsouto.salvarnuvem.domain.repository;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    DocumentEntity salvar(Document folder);
    Optional<DocumentEntity> findByName(String folderName);
    Optional<DocumentEntity> findById(String folderId);
    List<DocumentEntity> findRootFolders();
    List<DocumentEntity> findFolders();
}
