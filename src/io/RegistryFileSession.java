package io;

import machine.MachineRegistry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * <h2>Файлова „сесия“ за регистъра</h2>
 *
 * <p>
 * {@code RegistryFileSession} държи кой XML файл е „текущ“ за командата {@code save} (без параметри).
 * Това имитира поведение „отворен документ“: {@code open} / {@code close} / {@code save}.
 * </p>
 *
 * <p>
 * Реалното четене/писане се извършва от {@link io.MachineXmlStore}.
 * </p>
 */
public final class RegistryFileSession {
    private final MachineXmlStore xml;
    private Path currentFile;

    public RegistryFileSession(MachineXmlStore xml) {
        this.xml = Objects.requireNonNull(xml, "xml");
        this.currentFile = null;
    }

    /**
     * @return текущият файл или {@code null}, ако няма отворен файл
     */
    public Path currentFileOrNull() {
        return currentFile;
    }

    /**
     * Задава текущ файл (без да чете съдържание). Зареждането на реални данни се прави отделно.
     *
     * @param file път към файл
     */
    public void open(Path file) {
        this.currentFile = Objects.requireNonNull(file, "file");
    }

    /**
     * Забравя текущия файл (не трие данни, не трие регистър).
     */
    public void close() {
        this.currentFile = null;
    }

    /**
     * Записва регистъра в текущия файл.
     *
     * @param registry регистър за запис
     * @throws IOException ако няма текущ файл или при I/O грешка
     */
    public void save(MachineRegistry registry) throws IOException {
        if (currentFile == null) {
            throw new IOException("No file opened. Use saveas <file> first.");
        }
        xml.save(currentFile, registry);
    }

    /**
     * Записва регистъра в нов файл и го прави текущ.
     *
     * @param file нов файл
     * @param registry регистър за запис
     * @throws IOException при I/O грешка
     */
    public void saveAs(Path file, MachineRegistry registry) throws IOException {
        Objects.requireNonNull(file, "file");
        xml.save(file, registry);
        this.currentFile = file;
    }
}

