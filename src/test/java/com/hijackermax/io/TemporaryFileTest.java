package com.hijackermax.io;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.io.TemporaryFile;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TemporaryFileTest {
    @Test
    void testTemporaryFile() throws IOException {
        String testValue = "FooBar";
        Single<String> path = new Single<>();
        try (var tf = new TemporaryFile(testValue.getBytes());
             var fileReader = new FileReader(tf.getFile());
             var reader = new BufferedReader(fileReader)) {
            assertEquals(testValue, reader.readLine());
            path.setValue(tf.getPath());
        }
        assertTrue(path.containsValue());
        assertFalse(new File(path.getValue()).exists());
    }

    @Test
    void testTemporaryFileCustomExtension() throws IOException {
        String testValue = "FooBar";
        Single<String> path = new Single<>();
        try (var tf = new TemporaryFile(testValue.getBytes(), ".txt");
             var fileReader = new FileReader(tf.getFile());
             var reader = new BufferedReader(fileReader)) {
            assertEquals(testValue, reader.readLine());
            path.setValue(tf.getPath());
        }
        assertTrue(path.containsValue());
        assertTrue(path.getValue().endsWith(".txt"));
        assertFalse(new File(path.getValue()).exists());
    }
}
