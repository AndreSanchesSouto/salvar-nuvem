package br.com.devsouto.salvarnuvem.domain.model;

import br.com.devsouto.salvarnuvem.domain.enums.DocumentType;
import br.com.devsouto.salvarnuvem.infraestructure.persistence.entity.DocumentEntity;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DocumentFolder extends Document {

    private final List<Document> children = new ArrayList<Document>();

    public DocumentFolder() {}

    public DocumentFolder(String name, Path parentPath, Path localPath, DocumentType type) {
        super(null, name, parentPath, localPath, null, null, type);
    }

    public DocumentFolder(DocumentEntity documentEntity) {
        super(documentEntity.id(),
                documentEntity.name(),
                documentEntity.parentPath(),
                documentEntity.localPath(),
                documentEntity.remoteId(),
                documentEntity.remoteParentId(),documentEntity.type()
        );
   }

    @Override
    public Document createNewDocumentInMemory(String name, Path parentPath, Path localPath, DocumentType type) {
        // Instancia a classe concreta (DocumentFile) em vez da abstrata
        // Adiciona à lista de filhos (que aceita qualquer tipo de Document)
        return new DocumentFolder(name, parentPath, localPath, type);
    }

    public void addChildren(Document children) {
        this.children.add(children);
    }

    public List<Document> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "DocumentFolder: = " + this.getName() + "{" +
                "\nchildren=" + children +
                "\n}";
    }
}
