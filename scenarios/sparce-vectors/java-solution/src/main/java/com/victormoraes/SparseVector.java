package com.victormoraes;

public class SparseVector {

    private int[] indexes;
    private int[] values;

    public SparseVector(int[] arrayNumbers) {

        // O(2N) = O(N)
        int length = 0;
        for (int i = 0; i < arrayNumbers.length; i++) {
            if (arrayNumbers[i] != 0)
                length++;
        }

        indexes = new int[length];
        values = new int[length];
        int currentIndex = 0;
        for (int i = 0; i < arrayNumbers.length; i++) {
            if (arrayNumbers[i] != 0) {
                indexes[currentIndex] = i;
                values[currentIndex] = arrayNumbers[i];
                currentIndex++;
            }
        }
    }

    public int dotProduct(SparseVector vector) {

        int p1 = this.getIndexes().length > 0 ? this.getIndexes()[0] : 0;
        int p2 = vector.getIndexes().length > 0 ? vector.getIndexes()[0] : 0;

        if (this.getIndexes().length == 0 || vector.getIndexes().length == 0)
            return 0;

        int product = 0;

        while (p1 < getIndexes().length && p2 < vector.getIndexes().length) {

            if (p1 < p2)
                p1++;
            else if (p2 < p1)
                p2++;
            else {
                product += getValues()[p1] * vector.getValues()[p2];
                p1++;
                p2++;
            }

        }

        return product;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public int[] getValues() {
        return values;
    }
}
