package com.example.wordcounter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class WorkerPool implements Runnable {
    private final int numWorkers;
    private final BlockingQueue<String> pathQueue;
    private final FileReader fileReader;

        WorkerPool(int numWorkers, BlockingQueue<String> pathQueue, FileReader fileReader) {
        this.numWorkers = numWorkers;
        this.pathQueue = pathQueue;
        this.fileReader = fileReader;
    }

    public void run() {
        final List<Thread> workers = IntStream.range(0, numWorkers)
            .mapToObj(id -> new Thread(new Worker(id, pathQueue, fileReader)))
            .collect(Collectors.toList());

        workers.forEach(Thread::start);
        workers.forEach(this::join);

        fileReader.close();
    }

    private void join(final Thread thread) {
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
