package com.example.wordcounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


class WordCounter implements Runnable {
    private final BlockingQueue<String> wordQueue;
    private final Map<String, Integer> wordCounts;

    WordCounter(final BlockingQueue<String> wordQueue) {
        this.wordQueue = wordQueue;
        this.wordCounts = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                final String word = wordQueue.take();
                if (word.equals("")) { // Empty string is end-of-stream sign
                    break;
                }
                wordCounts.merge(word, 1, Integer::sum);
            }
        } catch (final InterruptedException e) {
            // TODO: DO something
        }
    }

    List<WordCount> get() {
        return new WordCountMapper().apply(wordCounts);
    }
}
