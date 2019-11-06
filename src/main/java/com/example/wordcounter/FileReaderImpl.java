package com.example.wordcounter;

// TODO: Real implementation, tests
class FileReaderImpl implements FileReader {
    private final Tokenizer tokenizer;

    FileReaderImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public void read(final String path) {
        tokenizer.tokenize(path);
    }

    @Override
    public void close() {
        tokenizer.close();
    }
}
