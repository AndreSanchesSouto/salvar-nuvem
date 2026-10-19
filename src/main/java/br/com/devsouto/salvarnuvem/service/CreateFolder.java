package br.com.devsouto.salvarnuvem.service;


import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CreateFolder {

    public static String createFolder(NetHttpTransport httpTransport) throws IOException, GeneralSecurityException {

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(
                    httpTransport,
                    GsonFactory.getDefaultInstance(),
                    DriveQuickstart.getCredentials(httpTransport)
                )
                .setApplicationName("Drive samples")
                .build();

        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName("TEsteeee");
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
}