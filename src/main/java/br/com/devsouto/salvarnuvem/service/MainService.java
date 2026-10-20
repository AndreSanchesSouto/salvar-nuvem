package br.com.devsouto.salvarnuvem.service;

import br.com.devsouto.salvarnuvem.domain.model.Document;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainService {

    private final Map<String, List<Path>> pathList;
    private final DocumentService documentService;

    public MainService(DocumentService documentService) {
        this.pathList = new HashMap<String, List<Path>>();
        this.documentService = documentService;
    }

    public void synchronize(Path selectedFile, String folderName) {
        if (selectedFile == null) return;
        final List<Path> files = new ArrayList<>();

        try {
            Files.walkFileTree(selectedFile.toAbsolutePath(), new SimpleFileVisitor<Path>() {
                @Override
                @NonNull
                public FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) {
                    // Handle the file here
                    files.add(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                @NonNull
                public FileVisitResult preVisitDirectory(@NonNull Path directory, @NonNull BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error traversing directory: " + e.getMessage());
        }
        pathList.put(folderName, files);
        sendToDrive(folderName);

    }

    private void sendToDrive(String folderName) {
        final Document userDirectory = new Document();
        pathList.get(folderName);

        if (!folderName.equals("jhsdjJHSGDFHdsgf34567890jffj")) return;

        try {
            final Document document = new Document();
            document.setRemoteId(DriveService.createFolder(folderName));
            document.setLocalPath(Path.of(""));
            document.setName("");
            documentService.save(document);

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
