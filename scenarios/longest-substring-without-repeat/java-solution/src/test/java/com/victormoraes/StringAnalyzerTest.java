package com.victormoraes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringAnalyzerTest {

    private StringAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new StringAnalyzer();
    }

    @ParameterizedTest(name = "\"{0}\" → {1}")
    @CsvSource({
        "abcabcbb, 3",   // duplicate mid-string, classic case
        "bbbbb,    1",   // all same character
        "pwwkew,   3",   // duplicate not at start
        "abcdef,   6",   // no duplicates, entire string is the window
        "a,        1",   // single character
        "'',       0"    // empty string
    })
    void lengthOfLongestSubstring(String input, int expected) {
        assertEquals(expected, analyzer.lengthOfLongestSubstring(input));
    }

    @ParameterizedTest
    @NullSource
    void returnsZeroForNull(String input) {
        assertEquals(0, analyzer.lengthOfLongestSubstring(input));
    }
}
