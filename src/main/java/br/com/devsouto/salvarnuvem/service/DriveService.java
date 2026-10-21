package br.com.devsouto.salvarnuvem.service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DriveService {

    static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport.Builder().build();


    private static Drive getDriveService() throws IOException {
        return new Drive.Builder(
                HTTP_TRANSPORT,
                GsonFactory.getDefaultInstance(),
                DriveQuickstart.getCredentials(HTTP_TRANSPORT)
        )
                .setApplicationName("Drive samples")
                .build();
    }

    public static String createFolder(String folderName) throws IOException, GeneralSecurityException {

        // Build a new authorized API client service.
        Drive service = getDriveService();

        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        try {
            File file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            System.out.println("Folder ID: " + file.getId());
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to create folder: " + e.getDetails());
            throw e;
        }
    }

    public static File uploadToFolder(String realFolderId, File filed) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();

        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName("photo.jpg");
        fileMetadata.setParents(Collections.singletonList(realFolderId));
        java.io.File filePath = new java.io.File("/home/andrew/Projects/salvar-nuvem/src/main/resources/files/sakura_wallpaper.jpg");
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }

}