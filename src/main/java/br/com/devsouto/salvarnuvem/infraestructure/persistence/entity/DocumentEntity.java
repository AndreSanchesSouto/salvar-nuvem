package br.com.devsouto.salvarnuvem.infraestructure.persistence.entity;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;

import java.nio.file.Path;

public record DocumentEntity(
        String id,
        String name,
        Path parentPath,
        Path localPath,
        String remoteId) { }
