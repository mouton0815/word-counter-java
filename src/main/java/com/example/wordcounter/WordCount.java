package com.example.wordcounter;

import java.text.Collator;
import java.util.Locale;
import java.util.Objects;


class WordCount implements Comparable<WordCount> {
    private final String word;
    private final int count;

    WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    String getWord() {
        return word;
    }

    int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WordCount wordCount = (WordCount) o;
        return count == wordCount.count &&
            Objects.equals(word, wordCount.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }

    @Override
    public int compareTo(final WordCount that) {
        if (this.count == that.count) {
            return Collator.getInstance(Locale.ENGLISH).compare(this.word, that.word);
        }
        return Integer.compare(that.count, this.count); // Descending order
    }

    @Override
    public String toString() {
        return "WordCount{" +
            "word='" + word + '\'' +
            ", count=" + count +
            '}';
    }
}
