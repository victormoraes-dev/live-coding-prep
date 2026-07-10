package com.victormoraes.practice.v1;

public class Vector {

    private int[] indexes;
    private int[] values;

    public Vector(int[] numbers) {

        int length = 0;

        // O(N)
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                length++;
            }
        }

        indexes = new int[length];
        values = new int[length];

        int localIndex = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                indexes[localIndex] = i;
                values[localIndex] = numbers[i];
                localIndex++;
            }
        }
    }

    public int getAffinity(Vector vector) {

        // Validations
        if (vector.getIndexes().length == 0 || getIndexes().length == 0)
            return 0;

        if (!(vector.getIndexes().length == getIndexes().length))
            return 0;

        // two pointer
        int p1 = getIndexes()[0];
        int p2 = vector.getIndexes()[0];
        int affinity = 0;

        while (p1 < getIndexes().length || p2 < vector.getIndexes().length) {

            if (p1 < p2) {
                p1++;
            } else if (p2 < p1) {
                p2++;
            } else {
                affinity += getValues()[p1] * vector.getValues()[p2];
                p1++;
                p2++;
            }
        }

        return affinity;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public int[] getValues() {
        return values;
    }

}
