package com.example.wordcounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


class WordCounter {
    private final BlockingQueue<String> wordQueue;
    private final Map<String, Integer> wordCounts;

    WordCounter(final BlockingQueue<String> wordQueue) {
        this.wordQueue = wordQueue;
        this.wordCounts = new HashMap<>();
    }

    List<WordCount> count() {
        try {
            while (true) {
                final String word = wordQueue.take();
                if (word.equals(STREAM_END)) { // Empty string is end-of-stream sign
                    break;
                }
                wordCounts.merge(word, 1, Integer::sum);
            }
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new WordCountMapper().apply(wordCounts);
    }
}
