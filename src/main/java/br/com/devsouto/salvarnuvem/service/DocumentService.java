package br.com.devsouto.salvarnuvem.service;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.repository.DocumentRepository;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.DocumentEntity;

public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document save(Document document) {
        DocumentEntity saved = this.repository.salvar(document);
        return new Document(
                saved.id(),
                saved.name(),
                saved.parentPath(),
                document.getLocalPath(),
                document.getRemoteId(),
                document.getType()
        );
    }

}
