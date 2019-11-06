package com.example.wordcounter;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static com.example.wordcounter.Constants.STREAM_END;


class Worker implements Runnable {
    private static final Logger log = Logger.getLogger(Worker.class.getName());

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
            log.info(String.format("Worker %d starts", id));
            String path;
            while (!(path = pathQueue.take()).isEmpty()) {
                log.info(String.format("Worker %d reads '%s'", id, path));
                fileReader.read(path);
            }
            // Inform other workers that pathQueue is empty
            pathQueue.put(STREAM_END);
            log.info(String.format("Worker %d leaves", id));
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
