package com.example.wordcounter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class Main {
    public static void main(String[] args) throws InterruptedException
    {
        final int numWorkers = Math.max(Runtime.getRuntime().availableProcessors() - 2, 1);
        System.out.printf("#workers: %d\n", numWorkers);

        final BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(1000);
        final BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>(1000);

        final FileReader fileReader = new FileReader(new Tokenizer(wordQueue));
        final WorkerPool workerPool = new WorkerPool(numWorkers, pathQueue, fileReader);
        new Thread(workerPool).start();

        pathQueue.put("foo bar");
        pathQueue.put("bar baz");
        pathQueue.put(STREAM_END);

        WordCounter counter = new WordCounter(wordQueue);
        List<WordCount> wordCounts = counter.count();
        for (final WordCount item: wordCounts) {
            System.out.printf("%3d - %s\n", item.getCount(), item.getWord());
        }
    }
}
