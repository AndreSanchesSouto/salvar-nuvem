package br.com.devsouto.salvarnuvem.domain.model;

import java.nio.file.Path;

public abstract class Document {

    private String id;
    private String name;
    private Path parentPath;
    private Path localPath;
    private String remoteId;

    public Document() {}

    public Document(String id, String name, Path parentPath, Path localPath, String remoteId) {
        this.id = id;
        this.name = name;
        this.parentPath = parentPath;
        this.localPath = localPath;
        this.remoteId = remoteId;
    }

    public abstract Document createNewDocumentInMemory(String name, Path parentPath, Path localPath);

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

    @Override
    public String toString() {
        return "Document {" +
                "\nid='" + id + '\'' +
                "\nname='" + name + '\'' +
                "\nparentPath=" + parentPath +
                "\nlocalPath=" + localPath +
                "\nremoteId='" + remoteId + '\'' +
                '}';
    }
}
