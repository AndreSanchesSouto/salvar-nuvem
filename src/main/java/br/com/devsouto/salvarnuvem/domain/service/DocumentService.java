package br.com.devsouto.salvarnuvem.domain.service;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.util.List;

public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public DocumentEntity save(Document document) {
        DocumentEntity saved = this.repository.salvar(document);
        return new DocumentEntity(
                saved.id(),
                saved.name(),
                saved.parentPath(),
                document.getLocalPath(),
                document.getRemoteId(),
                document.getRemoteParentId(),
                document.getType()
        );
    }

    public List<DocumentEntity> findRootFolders() {
        return this.repository.findRootFolders();
    }

}
