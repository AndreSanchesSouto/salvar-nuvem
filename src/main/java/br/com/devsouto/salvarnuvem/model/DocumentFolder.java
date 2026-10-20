package br.com.devsouto.salvarnuvem.model;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class DocumentFolder {

    private String id;
    private String name;
    private Path parentPath;
    private Path localPath;
    private String remoteId;

    public DocumentFolder() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getParentPath() {
        return parentPath;
    }

    public void setParentPath(Path parentPath) {
        this.parentPath = parentPath;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public Path getLocalPath() {
        return localPath;
    }

    public void setLocalPath(Path localPath) {
        this.localPath = localPath;
    }

}
