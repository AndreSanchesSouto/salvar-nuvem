package br.com.devsouto.salvarnuvem.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class MainService {

    final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport.Builder().build();
    private final List<Path> pathList;

    public MainService() {
        this.pathList = new ArrayList<Path>();
    }

    public void synchronize(File selectedFile, String folderName) {
        if (selectedFile == null) return;

        try {
            Files.walkFileTree(Path.of(selectedFile.getAbsolutePath()), new SimpleFileVisitor<Path>() {
                @Override
                @NonNull
                public FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) {
                    // Handle the file here
                    pathList.add(file);
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
        
        if(!pathList.isEmpty()) sendToDrive(folderName);

    }

    private void sendToDrive(String folderName) {

        this.pathList.stream().forEach(a -> System.out.println(a.getFileName()));

        if (!folderName.equals("jhsdjJHSGDFHdsgf34567890jffj")) return;

        try {

            final String folderId = CreateFolder.createFolder(HTTP_TRANSPORT);

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
