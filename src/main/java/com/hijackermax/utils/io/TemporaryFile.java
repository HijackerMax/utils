package com.hijackermax.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * Closeable wrapper for temporary files that can be used with try-with-resources
 */
public class TemporaryFile implements Closeable {
    private static final String FILE_NAME_SUFFIX = ".tmp";
    private final File file;

    public TemporaryFile(byte[] fileContent) throws IOException {
        file = File.createTempFile(String.valueOf(UUID.randomUUID()), FILE_NAME_SUFFIX);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(fileContent);
        }
    }

    @Override
    public void close() throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    public String getPath() {
        return file.getPath();
    }

    public File getFile() {
        return file;
    }
}
