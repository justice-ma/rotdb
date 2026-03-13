package com.rotdb;

import java.nio.charset.StandardCharsets;

public final class TestFiles {
    private TestFiles() {}

    public static String readResource(String path) {
        try (var in = TestFiles.class.getResourceAsStream(path)) {
            if (in == null) throw new IllegalArgumentException("Missing resource: " + path);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}