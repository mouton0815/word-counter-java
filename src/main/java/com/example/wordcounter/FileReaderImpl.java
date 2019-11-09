package com.example.wordcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


class FileReaderImpl implements FileReader {
    private final Tokenizer tokenizer;

    FileReaderImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public void read(final Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(tokenizer::tokenize);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        tokenizer.close();
    }
}
