package com.example.wordcounter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;


public class TokenizerTest {
    @Test
    public void testTokenizerEmpty() {
        tokenizeAndVerify("  ");
    }

    @Test
    public void testTokenizerTrim() {
        tokenizeAndVerify("  foo* ", "foo");
    }

    @Test
    public void testTokenizerMulti() {
        tokenizeAndVerify("Foo bAr baZ", "foo", "bar", "baz");
    }

    @Test
    public void testTokenizerSpecialSigns() {
        tokenizeAndVerify("Foo-~bar #!  baz", "foo", "bar", "baz");
    }

    @Test
    public void testTokenizerUnderscore() {
        tokenizeAndVerify("foo_bar__baz", "foo_bar__baz");
    }

    @Test
    public void testTokenizerUnicodeGerman() {
        tokenizeAndVerify("Fö bär baß", "fö", "bär", "baß");
    }

    @Test
    public void testTokenizerUnicodeRussian() {
        tokenizeAndVerify("Раз, два три!", "раз", "два", "три");
    }

    @Test
    public void testTokenizerUnicodeCzech() {
        tokenizeAndVerify("Jedna, dva tři čtyři pět!", "jedna", "dva", "tři", "čtyři", "pět");
    }

    @Test
    public void testTokenizerIgnoreNumbers() {
        tokenizeAndVerify("foo 123 bar456baz", "foo", "bar", "baz");
    }

    private List<String> tokenize(final String text) {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final Tokenizer tokenizer = new Tokenizer(queue);
        tokenizer.tokenize(text);
        tokenizer.close();

        return queue.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    private void tokenizeAndVerify(final String text, final String... refWords) {
        final List<String> words = tokenize(text);
        Assert.assertEquals(Arrays.asList(refWords), words);
    }
}
