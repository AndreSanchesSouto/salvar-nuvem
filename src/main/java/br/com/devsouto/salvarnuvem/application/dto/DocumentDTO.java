package br.com.devsouto.salvarnuvem.application.dto;

import java.nio.file.Path;

public record DocumentDTO(
        String name,
        Path localPath
){ }
