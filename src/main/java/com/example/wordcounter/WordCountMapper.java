package com.example.wordcounter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


class WordCountMapper implements Function<Map<String, Integer>, List<WordCount>> {
    @Override
    public List<WordCount> apply(final Map<String, Integer> map) {
        return map
            .entrySet()
            .stream()
            .map(e -> new WordCount(e.getKey(), e.getValue()))
            .sorted()
            .collect(Collectors.toList());
    }
}
