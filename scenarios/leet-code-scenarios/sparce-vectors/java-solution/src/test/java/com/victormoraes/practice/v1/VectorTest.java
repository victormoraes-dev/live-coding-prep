package com.victormoraes.practice.v1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    void testConstructorStoresNonZeroIndexesAndValues() {
        Vector v = new Vector(new int[] { 1, 0, 3 });
        assertArrayEquals(new int[] { 0, 2 }, v.getIndexes());
        assertArrayEquals(new int[] { 1, 3 }, v.getValues());
    }

    @Test
    void testConstructorAllZeros() {
        Vector v = new Vector(new int[] { 0, 0, 0 });
        assertArrayEquals(new int[] {}, v.getIndexes());
        assertArrayEquals(new int[] {}, v.getValues());
    }

    @Test
    void testGetAffinity() {
        Vector v1 = new Vector(new int[] { 1, 0, 3 });
        Vector v2 = new Vector(new int[] { 0, 2, 3 });
        assertEquals(9, v1.getAffinity(v2));
    }

    @Test
    void testDotProductNoOverlap() {
        Vector v1 = new Vector(new int[] { 1, 0, 0 });
        Vector v2 = new Vector(new int[] { 0, 2, 0 });
        assertEquals(0, v1.getAffinity(v2));
    }

    @Test
    void testDotProductBothEmpty() {
        Vector v1 = new Vector(new int[] { 0, 0 });
        Vector v2 = new Vector(new int[] { 0, 0 });
        assertEquals(0, v1.getAffinity(v2));
    }
}
