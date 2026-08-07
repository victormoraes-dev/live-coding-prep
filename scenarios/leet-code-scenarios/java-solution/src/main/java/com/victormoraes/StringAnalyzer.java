package com.victormoraes;

import java.util.Objects;

public class StringAnalyzer {

    /**
     * Finds the length of the longest substring without repeating characters.
     * 
     * @param s The input string
     * @return The length of the longest unique substring
     */
    public int lengthOfLongestSubstring(String s) {

        // Fail first
        // s is empty or null
        if (Objects.isNull(s) || s.isEmpty())
            return 0;

        // Sliding Window approach
        // SETUP
        int left = 0;
        int right = 0;

        int[] currentWindow = new int[128];
        int maxLength = 0;

        // abcabcbb
        while (right < s.length()) {

            char rightChar = s.charAt(right);
            currentWindow[rightChar]++;

            while (currentWindow[rightChar] > 1) {
                currentWindow[s.charAt(left)]--;
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }

        return maxLength;

    }
}
