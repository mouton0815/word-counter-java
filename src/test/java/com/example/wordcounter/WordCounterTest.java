package com.example.wordcounter;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class WordCounterTest {
    @Test
    public void testWordCounterEmpty() throws Exception {
        final BlockingQueue<String> queue = createWordQueue();
        final List<WordCount> list = ImmutableList.of();
        countAndVerify(queue, list);
    }

    @Test
    public void testWordCounterSingle() throws Exception {
        final BlockingQueue<String> queue = createWordQueue("foo");
        final List<WordCount> list = ImmutableList.of(new WordCount("foo", 1));
        countAndVerify(queue, list);
    }

    @Test
    public void testWordCounterMultiple() throws Exception {
        final BlockingQueue<String> queue = createWordQueue("bar", "foo", "bar");
        final List<WordCount> list = ImmutableList.of(new WordCount("bar", 2), new WordCount("foo", 1));
        countAndVerify(queue, list);
    }

    private BlockingQueue<String> createWordQueue(String... words) throws Exception {
        final BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>();
        for (final String word : words) {
            wordQueue.put(word);
        }
        wordQueue.put(""); // End-of-stream marker
        return wordQueue;
    }

    private void countAndVerify(final BlockingQueue<String> wordQueue, final List<WordCount> refList) {
        final WordCounter counter = new WordCounter(wordQueue);
        counter.run(); // TODO: Should return list directly
        final List<WordCount> list = counter.get();
        Assert.assertEquals(list, refList);
    }
}
