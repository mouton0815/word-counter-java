package com.example.wordcounter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    public static void main(String[] args) {
        final Path rootPath = Paths.get("./data");

        final int numWorkers = Math.max(Runtime.getRuntime().availableProcessors() - 2, 1);
        log.info(String.format("#workers: %d", numWorkers));

        final BlockingQueue<Path> pathQueue = new LinkedBlockingQueue<>(1000);
        final BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>(1000);

        final FileReader fileReader = new FileReaderImpl(new Tokenizer(wordQueue));
        final WorkerPool workerPool = new WorkerPool(numWorkers, pathQueue, fileReader);
        new Thread(workerPool).start();

        final PathCollector collector = new PathCollector(rootPath, pathQueue);
        new Thread(collector).start();

        final WordCounter counter = new WordCounter(wordQueue);
        final List<WordCount> wordCounts = counter.count();
        for (final WordCount item: wordCounts) {
            System.out.printf("%3d - %s\n", item.getCount(), item.getWord());
        }
    }
}
