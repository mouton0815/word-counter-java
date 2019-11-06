package com.example.wordcounter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class WorkerPoolTest {
    @Test
    public void testWorkerPoolNoInput() throws InterruptedException {
        runWorkerPoolAndVerify(0);
    }

    @Test
    public void testWorkerPoolWithInput() throws InterruptedException {
        runWorkerPoolAndVerify(100);
    }

    private void runWorkerPoolAndVerify(final int pathCount) throws InterruptedException {
        final BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(pathCount + 1);
        final List<String> refList = new LinkedList<>();
        for (int i = 0; i < pathCount; i++) {
            final String path = String.format("%02d", i);
            refList.add(path);
            pathQueue.put(path);
        }
        pathQueue.put(STREAM_END);

        final FileReaderMock fileReader = new FileReaderMock();
        final WorkerPool workerPool = new WorkerPool(4, pathQueue, fileReader);
        workerPool.run();

        Collections.sort(fileReader.get()); // Results may arrive unordered
        Assert.assertEquals(refList, fileReader.get());
    }
}
