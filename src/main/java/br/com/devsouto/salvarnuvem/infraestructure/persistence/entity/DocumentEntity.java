package br.com.devsouto.salvarnuvem.infraestructure.persistence.entity;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;
import br.com.devsouto.salvarnuvem.domain.model.Document;

import java.nio.file.Path;

public record DocumentEntity(
        String id,
        String name,
        Path parentPath,
        Path localPath,
        String remoteId
) {
    public DocumentEntity(Document document) {
        this(document.getId(), document.getName(), document.getParentPath(), document.getLocalPath(), document.getRemoteId());
    }
}
