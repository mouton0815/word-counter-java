package com.example.wordcounter;

import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Tokenizer {
    private final BlockingQueue<String> wordQueue;
    private final Pattern pattern;

    Tokenizer(final BlockingQueue<String> wordQueue) {
        this.wordQueue = wordQueue;
        this.pattern = Pattern.compile("[\\p{L}_]+");
    }

    void tokenize(final String text) {
        final Matcher matcher = pattern.matcher(text);
        try {
            while (matcher.find()) {
                wordQueue.put(matcher.group().toLowerCase());
            }
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void close() {
        try {
            wordQueue.put("");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
