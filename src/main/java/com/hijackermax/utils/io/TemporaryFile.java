package com.hijackermax.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * Closeable wrapper for temporary files that can be used with try-with-resources
 *
 * @since 0.0.1
 */
public class TemporaryFile implements Closeable {
    private static final String FILE_NAME_SUFFIX = ".tmp";
    private final File file;

    /**
     * Create temporary file with provided contents and wraps it with new instance of Temporary file wrapper class
     *
     * @param fileContent contents of temporary file
     * @throws IOException in case of temporary file creation issues
     * @since 0.0.1
     */
    public TemporaryFile(byte[] fileContent) throws IOException {
        file = File.createTempFile(String.valueOf(UUID.randomUUID()), FILE_NAME_SUFFIX);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(fileContent);
        }
    }

    /**
     * Create temporary file with provided contents and extension and wraps it with new instance of Temporary file wrapper class
     *
     * @param fileContent contents of temporary file
     * @param nameSuffix  extension of temporary file
     * @throws IOException in case of temporary file creation issues
     * @since 0.0.2
     */
    public TemporaryFile(byte[] fileContent, String nameSuffix) throws IOException {
        file = File.createTempFile(String.valueOf(UUID.randomUUID()), nameSuffix);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(fileContent);
        }
    }

    @Override
    public void close() throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    /**
     * Returns temporary file path
     *
     * @return {@link String} representation of temporary file path
     */
    public String getPath() {
        return file.getPath();
    }

    /**
     * Returns instance of temporary file
     *
     * @return created temporary file instance
     */
    public File getFile() {
        return file;
    }
}
