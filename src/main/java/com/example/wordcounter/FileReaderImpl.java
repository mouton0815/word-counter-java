package com.example.wordcounter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        // Avoid MalformedInputException with charset ISO-8859-1, see https://stackoverflow.com/a/44233948
        try (Stream<String> stream = Files.lines(path, StandardCharsets.ISO_8859_1)) {
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
