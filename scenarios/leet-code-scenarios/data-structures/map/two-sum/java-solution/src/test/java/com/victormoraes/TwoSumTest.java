package com.victormoraes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TwoSumTest {

    private TwoSum twoSum;

    @BeforeEach
    void setUp() {
        twoSum = new TwoSum();
    }

    @Test
    void shouldReturnIndicesForTheStandardExample() {
        assertArrayEquals(new int[] {0, 1}, twoSum.twoSum(new int[] {2, 7, 11, 15}, 9));
    }

    @Test
    void shouldReturnIndicesAtOppositeEndsOfTheArray() {
        assertArrayEquals(new int[] {0, 4}, twoSum.twoSum(new int[] {3, 8, 12, 20, 4}, 7));
    }

    @Test
    void shouldUseTwoDifferentOccurrencesWhenValuesAreEqual() {
        assertArrayEquals(new int[] {0, 1}, twoSum.twoSum(new int[] {3, 3}, 6));
    }

    @Test
    void shouldFindAPairContainingNegativeNumbers() {
        assertArrayEquals(new int[] {0, 2}, twoSum.twoSum(new int[] {-3, 4, 3, 90}, 0));
    }

    @Test
    void shouldFindAPairContainingZero() {
        assertArrayEquals(new int[] {0, 2}, twoSum.twoSum(new int[] {0, 4, 5}, 5));
    }

    @Test
    void shouldReturnEmptyArrayWhenNoPairAddsUpToTarget() {
        assertArrayEquals(new int[] {}, twoSum.twoSum(new int[] {1, 2, 3}, 10));
    }

    @Test
    void shouldReturnEmptyArrayForAnEmptyInput() {
        assertArrayEquals(new int[] {}, twoSum.twoSum(new int[] {}, 1));
    }

    @Test
    void shouldNotReuseTheOnlyElement() {
        assertArrayEquals(new int[] {}, twoSum.twoSum(new int[] {5}, 10));
    }

}
