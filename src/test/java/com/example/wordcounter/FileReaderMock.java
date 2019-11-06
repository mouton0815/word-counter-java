package com.example.wordcounter;

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
    public void read(String path) {
        result.add(path);
    }

    @Override
    public void close() {
        // Do nothing
    }
}
