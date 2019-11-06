package com.example.wordcounter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import static com.example.wordcounter.Constants.STREAM_END;


public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        final int numWorkers = Math.max(Runtime.getRuntime().availableProcessors() - 2, 1);
        log.info(String.format("#workers: %d", numWorkers));

        final BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(1000);
        final BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>(1000);

        final FileReader fileReader = new FileReaderImpl(new Tokenizer(wordQueue));
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
