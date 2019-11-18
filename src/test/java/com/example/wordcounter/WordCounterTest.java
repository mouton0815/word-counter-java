package com.example.wordcounter;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class WordCounterTest {
    @Test
    public void testWordCounterEmpty() throws Exception {
        final BlockingQueue<String> queue = createWordQueue();
        final List<WordCount> refList = ImmutableList.of();
        countAndVerify(queue, refList);
    }

    @Test
    public void testWordCounterSingle() throws Exception {
        final BlockingQueue<String> queue = createWordQueue("foo");
        final List<WordCount> refList = ImmutableList.of(new WordCount("foo", 1));
        countAndVerify(queue, refList);
    }

    @Test
    public void testWordCounterMultiple() throws Exception {
        final BlockingQueue<String> queue = createWordQueue("bar", "foo", "bar");
        final List<WordCount> refList = ImmutableList.of(new WordCount("bar", 2), new WordCount("foo", 1));
        countAndVerify(queue, refList);
    }

    private BlockingQueue<String> createWordQueue(String... words) throws Exception {
        final BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>();
        for (final String word : words) {
            wordQueue.put(word);
        }
        wordQueue.put(STREAM_END); // End-of-stream marker
        return wordQueue;
    }

    private void countAndVerify(final BlockingQueue<String> wordQueue, final List<WordCount> refList) {
        final WordCounter counter = new WordCounter(wordQueue);
        final List<WordCount> list = counter.count();
        Assert.assertEquals(list, refList);
    }
}
