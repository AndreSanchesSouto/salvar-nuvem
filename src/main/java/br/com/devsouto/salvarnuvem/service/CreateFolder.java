package br.com.devsouto.salvarnuvem.service;


import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;

/* Class to demonstrate use of Drive's create folder API */
public class CreateFolder {


    /**
     * Create new folder.
     *
     * @return Inserted folder id if successful, {@code null} otherwise.
     * @throws IOException if service account credentials file not found.
     */

    public static void main(String[] args) {
        try {
            String name = createFolder();
            System.out.println(name);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static String createFolder() throws IOException, GeneralSecurityException {
        // Load pre-authorized user credentials from the environment.
        // TODO(developer) - See https://developers.google.com/identity for
        // guides on implementing OAuth2 for your application.

        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport.Builder().build();

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(
                    HTTP_TRANSPORT,
                    GsonFactory.getDefaultInstance(),
                    DriveQuickstart.getCredentials(HTTP_TRANSPORT)
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