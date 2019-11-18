package com.example.wordcounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static com.example.wordcounter.Constants.STREAM_END;


class WordCounter {
    private static final Logger log = Logger.getLogger(WordCounter.class.getName());

    private final BlockingQueue<String> wordQueue;
    private final Map<String, Integer> wordCounts;

    WordCounter(final BlockingQueue<String> wordQueue) {
        this.wordQueue = wordQueue;
        this.wordCounts = new HashMap<>();
    }

    List<WordCount> count() {
        try {
            String word;
            while (!(word = wordQueue.take()).equals(STREAM_END)) {
                wordCounts.merge(word, 1, Integer::sum);
            }
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Counter leaves");
        return new WordCountMapper().apply(wordCounts);
    }
}
