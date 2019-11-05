package com.example.wordcounter;

import java.util.concurrent.BlockingQueue;


class Worker implements Runnable {
    private final int id;
    private final BlockingQueue<String> pathQueue;
    private final FileReader fileReader;
    private final BlockingQueue<Boolean> readyQueue;

    Worker(final int id, final BlockingQueue<String> pathQueue, final FileReader fileReader, final BlockingQueue<Boolean> readyQueue) {
        this.id = id;
        this.pathQueue = pathQueue;
        this.fileReader = fileReader;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Worker %d starts\n", id);
            while (true) {
                final String path = pathQueue.take();
                if (path.isEmpty()) {
                    readyQueue.put(true);
                    break;
                }
                fileReader.read(path);
            }
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
