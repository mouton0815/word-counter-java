package com.example.wordcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import static com.example.wordcounter.Constants.STREAM_END;


class PathCollector implements Runnable {
    private final Path rootPath;
    private final BlockingQueue<Path> pathQueue;

    PathCollector(final Path rootPath, final BlockingQueue<Path> pathQueue) {
        this.rootPath = rootPath;
        this.pathQueue = pathQueue;
    }

    @Override
    public void run() {
        try (Stream<Path> paths = Files.walk(rootPath)) {
            paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith("txt"))
                .forEach(this::put);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        put(Paths.get(STREAM_END));
    }

    private void put(final Path path) {
        try {
            pathQueue.put(path);
        }
        catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
