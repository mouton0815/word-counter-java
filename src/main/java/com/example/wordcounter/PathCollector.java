package com.example.wordcounter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static com.example.wordcounter.Constants.STREAM_END;


class PathCollector implements Runnable {
    private static final Logger log = Logger.getLogger(PathCollector.class.getName());

    private final Path rootPath;
    private final BlockingQueue<Path> pathQueue;

    PathCollector(final Path rootPath, final BlockingQueue<Path> pathQueue) {
        this.rootPath = rootPath;
        this.pathQueue = pathQueue;
    }

    @Override
    public void run() {
        try {
            // Note: Files.walk is broken, see https://stackoverflow.com/a/22868706
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (attrs.isRegularFile() && file.toString().toLowerCase().endsWith(".txt")) {
                        put(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    log.warning(e.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            put(Paths.get(STREAM_END));
        }
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
