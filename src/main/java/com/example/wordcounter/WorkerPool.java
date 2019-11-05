package com.example.wordcounter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


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
        final BlockingQueue<Boolean> readyQueue = new LinkedBlockingQueue<>(numWorkers);

        // Spawn workers ...
        for (int i = 0; i < numWorkers; i++) {
            new Thread(new Worker(i, pathQueue, fileReader, readyQueue)).start();
        }

        // ... and wait for their termination
        int workerCount = numWorkers;
        try {
            while (true) {
                readyQueue.take();
                if (--workerCount == 0) {
                    fileReader.close();
                    return;
                }
            }
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
