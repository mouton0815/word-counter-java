package com.example.wordcounter;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.wordcounter.Constants.STREAM_END;


public class FileReaderTest {
    @Test
    public void testFileReaderSuccess() throws InterruptedException {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final Tokenizer tokenizer = new Tokenizer(queue);
        final FileReader fileReader = new FileReaderImpl(tokenizer);
        fileReader.read(Paths.get("./data/file1.txt"));
        fileReader.close();

        String word;
        final List<String> list = new LinkedList<>();
        while (!(word = queue.take()).equals(STREAM_END)) {
            list.add(word);
        }

        Assert.assertEquals(100, list.size());
        Assert.assertEquals("lorem", list.get(0));
        Assert.assertEquals("amet", list.get(list.size() - 1));
    }

    @Test
    public void testFileReaderNotFound() {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final Tokenizer tokenizer = new Tokenizer(queue);
        final FileReader fileReader = new FileReaderImpl(tokenizer);
        try {
            fileReader.read(Paths.get("./does/not.exist"));
            Assert.fail();
        }
        catch (final Exception e) {
            Assert.assertNotNull(e.getCause());
            Assert.assertEquals(NoSuchFileException.class, e.getCause().getClass());
        }
    }
}
