package br.com.devsouto.salvarnuvem.domain.model;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;

import java.nio.file.Path;

public abstract class Document {

    private String id;
    private String name;
    private Path parentPath;
    private Path localPath;
    private String remoteId;
    private DocumentType type;

    public Document() {}

    public Document(String id, String name, Path parentPath, Path localPath, String remoteId, DocumentType type) {
        this.id = id;
        this.name = name;
        this.parentPath = parentPath;
        this.localPath = localPath;
        this.remoteId = remoteId;
        this.type = type;
    }

    public abstract Document createNewDocumentInMemory(String name, Path parentPath, Path localPath, DocumentType type);

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

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
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
