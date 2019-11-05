package com.example.wordcounter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;


public class WordCountMapperTest {
    @Test
    public void testWordSliceSortingEmpty() {
        final Map<String, Integer> map = ImmutableMap.of();
        final List<WordCount> list = ImmutableList.of();
        mapAndVerify(map, list);
    }

    @Test
    public void testWordSliceSortingSimple() {
        final Map<String, Integer> map = ImmutableMap.of("foo", 1);
        final List<WordCount> list = ImmutableList.of(new WordCount("foo", 1));
        mapAndVerify(map, list);
    }

    @Test
    public void testWordSliceSortingSecondary() {
        final Map<String, Integer> map = ImmutableMap.of("foo", 2, "bar", 1, "baz", 2);
        final List<WordCount> list = ImmutableList.of(
            new WordCount("baz", 2),
            new WordCount("foo", 2),
            new WordCount("bar", 1));
        mapAndVerify(map, list);
    }

    @Test
    public void testWordSliceSortingUnicode() {
        final Map<String, Integer> map = ImmutableMap.of("über", 1, "Zuse", 1, "Ödem", 1);
        final List<WordCount> list = ImmutableList.of(
            new WordCount("Ödem", 1),
            new WordCount("über", 1),
            new WordCount("Zuse", 1));
        mapAndVerify(map, list);
    }

    private void mapAndVerify(final Map<String, Integer> map, final List<WordCount> refList) {
        final List<WordCount> list = new WordCountMapper().apply(map);
        Assert.assertEquals(refList, list);
    }
}
