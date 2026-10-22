package br.com.devsouto.salvarnuvem.domain.service;


import br.com.devsouto.salvarnuvem.domain.model.Document;
import br.com.devsouto.salvarnuvem.domain.model.DocumentFile;
import br.com.devsouto.salvarnuvem.domain.model.DocumentFolder;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class DriveService {

    /**
     * Application name.
     */
    private  final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private  final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private  final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private  final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private  final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    private final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport.Builder().build();


    public  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private  Drive getDriveService() throws IOException {
        return new Drive.Builder(
                HTTP_TRANSPORT,
                GsonFactory.getDefaultInstance(),
                getCredentials(HTTP_TRANSPORT)
        )
                .setApplicationName("Drive samples")
                .build();
    }

    public String createFolder(DocumentFolder folder) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final Drive service = getDriveService();

        if(folder.getLocalParentPath() == null) {
            return this.createRootFolder(service, folder);
        } else {
            return this.createChildFolder(service, folder);
        }
    }

    private String createRootFolder(Drive service, DocumentFolder folder) throws IOException {
        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName(folder.getName());
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        try {
            File file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to create folder: " + e.getDetails());
            throw e;
        }
    }

    private String createChildFolder(Drive service, DocumentFolder folder) throws IOException {
        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName(folder.getName());
        fileMetadata.setParents(Collections.singletonList(folder.getRemoteParentId()));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        try {
            File file = service.files().create(fileMetadata)
                    .setFields("id, parents")
                    .execute();
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to create folder: " + e.getDetails());
            throw e;
        }
    }

    public String uploadToFolder(String realFolderId, DocumentFile documentFile) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();

        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName(documentFile.getName());
        fileMetadata.setParents(Collections.singletonList(realFolderId));
        java.io.File filePath = new java.io.File(documentFile.getLocalPath().toString());
        Path documentFilePath = documentFile.getLocalPath();
        String mimeType = Files.probeContentType(documentFilePath);
        FileContent mediaContent = new FileContent(mimeType, filePath);
        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }

    public  void m() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                getCredentials(HTTP_TRANSPORT)
        )
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }

}