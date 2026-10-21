package br.com.devsouto.salvarnuvem.domain.model;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;

import java.nio.file.Path;

public class DocumentFile extends Document {

    public DocumentFile() {}

    public DocumentFile(String id, String name, Path parentPath, Path localPath, String remoteId, DocumentType type) {
        super(id, name, parentPath, localPath, remoteId, type);
    }

    @Override
    public Document createNewDocumentInMemory(String name, Path parentPath, Path path, DocumentType type) {
        return new DocumentFile(null, name, parentPath, path, null, type);
    }

    @Override
    public String toString() {
        return "DocumentFile{" +
                    "\nnome: " + this.getName() +
                    "\npath: " + this.getLocalPath() +
                    "\nparent: " + this.getParentPath() +
                "\n}";
    }
}
