package com.example.wordcounter;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class WorkerTest {
    @Test
    public void testWorkerEmpty() throws InterruptedException {
        workAndVerify();
    }

    @Test
    public void testWorkerNormal() throws InterruptedException {
        workAndVerify("foo", "bar", "baz");
    }

    private void workAndVerify(String... paths) throws InterruptedException {
        final BlockingQueue<Path> pathQueue = new LinkedBlockingQueue<>();
        final FileReaderMock fileReader = new FileReaderMock();
        final Worker worker = new Worker(1, pathQueue, fileReader);

        final Thread thread = new Thread(worker);
        thread.start();

        for (String path: paths) {
            pathQueue.put(Paths.get(path));
        }
        pathQueue.put(Paths.get(STREAM_END));

        thread.join();

        Assert.assertEquals(Arrays.asList(paths), fileReader.get());
    }
}
