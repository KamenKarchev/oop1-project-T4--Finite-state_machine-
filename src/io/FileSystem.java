interface FileSystem {
    void openFile(File file);
    void closeFile();
    void saveFile(File file);
    void saveAsFile(File file);
}