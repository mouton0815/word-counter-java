package com.example.wordcounter;

// TODO: Real implementation, tests
class FileReader {
    private final Tokenizer tokenizer;

    FileReader(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    void read(final String path) {
        tokenizer.tokenize(path);
    }

    void close() {
        tokenizer.close();
    }
}
