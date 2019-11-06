package com.example.wordcounter;

import java.util.concurrent.BlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


class Worker implements Runnable {
    private final int id;
    private final BlockingQueue<String> pathQueue;
    private final FileReader fileReader;

    Worker(final int id, final BlockingQueue<String> pathQueue, final FileReader fileReader) {
        this.id = id;
        this.pathQueue = pathQueue;
        this.fileReader = fileReader;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Worker %d starts\n", id);
            String path;
            while (!(path = pathQueue.take()).isEmpty()) {
                System.out.printf("Worker %d reads '%s'\n", id, path);
                fileReader.read(path);
            }
            // Inform other workers that pathQueue is empty
            pathQueue.put(STREAM_END);
            System.out.printf("Worker %d leaves\n", id);
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
