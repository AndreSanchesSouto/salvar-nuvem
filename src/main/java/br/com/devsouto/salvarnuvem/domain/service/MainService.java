package br.com.devsouto.salvarnuvem.domain.service;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.model.DocumentFile;
import br.com.devsouto.salvarnuvem.domain.model.DocumentFolder;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.GeneralSecurityException;
import java.util.*;

public class MainService {

    private final DocumentService documentService;

    public MainService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void synchronize(Path source, String folderName) {
        Path sourcePath = source.toAbsolutePath().normalize();

        if (!Files.exists(sourcePath)) {
            throw new RuntimeException("Caminho não existente!");
        }

        final String fileName = !folderName.isEmpty() ? folderName : sourcePath.getFileName().toString();
        final Document rootDirectory = new DocumentFolder()
                .createNewDocumentInMemory(
                        fileName,
                        null,
                        sourcePath
                );
        final Stack<DocumentFolder> directories = new Stack<>();
        directories.push((DocumentFolder) rootDirectory);

        try {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {

                @Override
                @NonNull
                public FileVisitResult preVisitDirectory(@NonNull Path directory, @NonNull BasicFileAttributes attrs) {
                    if (directory.toAbsolutePath().toString().equals(rootDirectory.getLocalPath().toString())) {
                        return FileVisitResult.CONTINUE;
                    }

                    final Document newDirectory = new DocumentFolder()
                            .createNewDocumentInMemory(
                                    directory.getFileName().toString(),
                                    directory.getParent(),
                                    directory
                            );
                    directories.peek().addChildren(newDirectory);
                    directories.push((DocumentFolder) newDirectory);

                    // Handle the file here
                    return FileVisitResult.CONTINUE;
                }

                @Override
                @NonNull
                public FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) {
                    final Document newFile = new DocumentFile()
                            .createNewDocumentInMemory(
                                    file.getFileName().toString(),
                                    file.getParent(),
                                    file
                            );
                    directories.peek().addChildren(newFile);
                    // Handle the file here
                    return FileVisitResult.CONTINUE;
                }

                @Override
                @NonNull
                public FileVisitResult postVisitDirectory(@NonNull Path directory, IOException exception) {
                    if (!directory.equals(sourcePath)) {
                        directories.pop();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error traversing directory: " + e.getMessage());
        }
        System.out.println(directories);
        directories.forEach(this.documentService::save);
        //sendToDrive(folderName);

    }

    private void sendToDrive(String folderName) {
        final Document userDirectory = new DocumentFile();
        if (!folderName.equals("jhsdjJHSGDFHdsgf34567890jffj")) return;

        try {
            final DocumentFile folder = new DocumentFile();
            folder.setRemoteId(DriveService.createFolder(folderName));
            folder.setLocalPath(Path.of(""));
            folder.setName(folderName);
            documentService.save(folder);

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
