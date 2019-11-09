package com.example.wordcounter;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


class FileReaderMock implements FileReader {
    private final List<String> result;

    FileReaderMock() {
        this.result = new LinkedList<>();
    }

    List<String> get() {
        return result;
    }

    @Override
    public void read(Path path) {
        result.add(path.toString());
    }

    @Override
    public void close() {
        // Do nothing
    }
}
