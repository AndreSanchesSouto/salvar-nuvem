package br.com.devsouto.salvarnuvem.domain.model;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;

import java.nio.file.Path;

public abstract class Document {

    private String id;
    private String name;
    private Path localParentPath;
    private Path localPath;
    private String remoteId;
    private String remoteParentId;
    private DocumentType type;

    public Document() {}

    public Document(String id, String name, Path localParentPath, Path localPath, String remoteId, String remoteParentId, DocumentType type) {
        this.id = id;
        this.name = name;
        this.localParentPath = localParentPath;
        this.localPath = localPath;
        this.remoteId = remoteId;
        this.remoteParentId = remoteParentId;
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

    public Path getLocalParentPath() {
        return localParentPath;
    }

    public void setLocalParentPath(Path localParentPath) {
        this.localParentPath = localParentPath;
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

    public String getRemoteParentId() {
        return remoteParentId;
    }

    public void setRemoteParentId(String remoteParentId) {
        this.remoteParentId = remoteParentId;
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
                "\nparentPath=" + localParentPath +
                "\nlocalPath=" + localPath +
                "\nremoteId='" + remoteId + '\'' +
                '}';
    }
}
