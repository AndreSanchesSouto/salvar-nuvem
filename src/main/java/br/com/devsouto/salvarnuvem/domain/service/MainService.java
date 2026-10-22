package br.com.devsouto.salvarnuvem.domain.service;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;
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
    private final DriveService driveService;

    public MainService(DocumentService documentService, DriveService driveService) {
        this.documentService = documentService;
        this.driveService = driveService;
    }

    public void synchronize(Path source, String folderName)  {
        Path sourcePath = source.toAbsolutePath().normalize();

        if (!Files.exists(sourcePath)) {
            throw new RuntimeException("Caminho não existente!");
        }

        final String fileName = !folderName.isEmpty() ? folderName : sourcePath.getFileName().toString();
        final Document rootDirectory = new DocumentFolder()
                .createNewDocumentInMemory(
                        fileName,
                        null,
                        sourcePath,
                        DocumentType.FOLDER
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
                                    directory,
                                    DocumentType.FOLDER
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
                                    file,
                                    DocumentType.FILE
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


        directories.forEach(this::saveDocuments);
    }

    private void saveDocuments(Document document) {
        final Stack<String> stackIds = new Stack<>();
        try {
            if (document instanceof DocumentFolder folder)
                this.sendFolderToDrive(folder, stackIds);

            else if (document instanceof DocumentFile file)
                this.sendFileToDrive(file, stackIds);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        this.documentService.save(document);
    }

    private void sendFolderToDrive(DocumentFolder folder, Stack<String> stackIds) throws GeneralSecurityException, IOException {
        final String directoryId;

        if (folder.getLocalParentPath() != null)
            folder.setRemoteParentId(stackIds.peek());

        directoryId = this.driveService.createFolder(folder);
        folder.setRemoteId(directoryId);
        stackIds.push(directoryId);
        folder.getChildren().forEach(this::saveDocuments);
        stackIds.pop();
    }

    private void sendFileToDrive(DocumentFile file, Stack<String> stackIds) throws IOException {
        final String parentId = stackIds.peek();
        file.setRemoteParentId(parentId);
        file.setRemoteId(this.driveService.uploadToFolder(file.getRemoteParentId(), file));
    }

}
