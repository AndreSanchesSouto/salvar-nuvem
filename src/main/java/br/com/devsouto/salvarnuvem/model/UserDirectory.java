package br.com.devsouto.salvarnuvem.model;

import java.io.File;

public class UserDirectory {
    private File directory;

    public UserDirectory(File directory) {
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }
}
