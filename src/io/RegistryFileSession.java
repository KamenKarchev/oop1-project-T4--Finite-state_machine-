package io;

import machine.MachineRegistry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Holds the current opened registry file (like a session).\n
 * Save writes the whole registry to the current file.
 */
public final class RegistryFileSession {
    private final MachineXmlStore xml;
    private Path currentFile;

    public RegistryFileSession(MachineXmlStore xml) {
        this.xml = Objects.requireNonNull(xml, "xml");
        this.currentFile = null;
    }

    public Path currentFileOrNull() {
        return currentFile;
    }

    public void open(Path file) {
        this.currentFile = Objects.requireNonNull(file, "file");
    }

    public void close() {
        this.currentFile = null;
    }

    public void save(MachineRegistry registry) throws IOException {
        if (currentFile == null) {
            throw new IOException("No file opened. Use saveas <file> first.");
        }
        xml.save(currentFile, registry);
    }

    public void saveAs(Path file, MachineRegistry registry) throws IOException {
        Objects.requireNonNull(file, "file");
        xml.save(file, registry);
        this.currentFile = file;
    }
}

