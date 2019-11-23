package com.example.wordcounter;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class PathCollectorTest {
    @Test
    public void testPathCollector() throws InterruptedException {
        final BlockingQueue<Path> queue = new LinkedBlockingQueue<>();
        final PathCollector pathCollector = new PathCollector(Paths.get("./data"), queue);
        pathCollector.run();

        Path path;
        final List<Path> list = new LinkedList<>();
        while (!(path = queue.take()).equals(Paths.get(STREAM_END))) {
            list.add(path);
        }
        Collections.sort(list);

        final List<Path> refList =
            ImmutableList.of(Paths.get("./data/file1.txt"), Paths.get("./data/file2.txt"), Paths.get("./data/subdir/file3.txt"));
        Assert.assertEquals(refList, list);
    }
}
