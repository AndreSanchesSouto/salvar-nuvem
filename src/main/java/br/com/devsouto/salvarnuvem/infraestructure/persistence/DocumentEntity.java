package br.com.devsouto.salvarnuvem.infraestructure.persistence;

import java.nio.file.Path;

public record DocumentEntity(
        String id,
        String name,
        Path parentPath,
        Path localPath,
        String remoteId
) { }
