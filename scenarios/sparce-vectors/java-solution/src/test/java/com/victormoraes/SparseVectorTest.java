package com.victormoraes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SparseVectorTest {

    @Test
    void testConstructorStoresNonZeroIndexesAndValues() {
        SparseVector v = new SparseVector(new int[]{1, 0, 3});
        assertArrayEquals(new int[]{0, 2}, v.getIndexes());
        assertArrayEquals(new int[]{1, 3}, v.getValues());
    }

    @Test
    void testConstructorAllZeros() {
        SparseVector v = new SparseVector(new int[]{0, 0, 0});
        assertArrayEquals(new int[]{}, v.getIndexes());
        assertArrayEquals(new int[]{}, v.getValues());
    }

    @Test
    void testDotProduct() {
        SparseVector v1 = new SparseVector(new int[]{1, 0, 3});
        SparseVector v2 = new SparseVector(new int[]{0, 2, 3});
        assertEquals(9, v1.dotProduct(v2));
    }

    @Test
    void testDotProductNoOverlap() {
        SparseVector v1 = new SparseVector(new int[]{1, 0, 0});
        SparseVector v2 = new SparseVector(new int[]{0, 2, 0});
        assertEquals(0, v1.dotProduct(v2));
    }

    @Test
    void testDotProductBothEmpty() {
        SparseVector v1 = new SparseVector(new int[]{0, 0});
        SparseVector v2 = new SparseVector(new int[]{0, 0});
        assertEquals(0, v1.dotProduct(v2));
    }
}
