package com.victormoraes;

public class StringAnalyzer {

    public String findShortestJourney(String session, String target) {
        // Edge cases: if session is shorter than target, it's impossible.
        if (session == null || target == null || session.length() < target.length() || target.isEmpty()) {
            return "";
        }

        // 1. SETUP: Create a frequency map for the target events.
        // We use a 128-size int array (ASCII) instead of a HashMap to ensure O(1) space
        // and zero GC overhead.
        int[] targetCounts = new int[128];
        for (char c : target.toCharArray()) {
            targetCounts[c]++;
        }

        int left = 0;
        int right = 0;

        int requiredEvents = target.length(); // Total number of specific events we need to match
        int matchedEvents = 0; // How many required events we currently have in our window

        int minLength = Integer.MAX_VALUE;
        int minStart = 0; // Tracks where our shortest valid window begins

        // Tracks the characters currently inside our sliding window
        int[] windowCounts = new int[128];

        // 2. EXPAND: Move the right pointer to explore the session
        while (right < session.length()) {
            char rightChar = session.charAt(right);
            windowCounts[rightChar]++;

            // If the character we just added is part of the target AND we actually needed it
            // (meaning we haven't collected more of it than required), we count it as a match.
            if (targetCounts[rightChar] > 0 && windowCounts[rightChar] <= targetCounts[rightChar]) {
                matchedEvents++;
            }

            // 3. CONTRACT: When our window has ALL the required events, try to shrink it
            while (matchedEvents == requiredEvents) {
                int currentWindowSize = right - left + 1;

                // Record the new minimum window if it's the shortest we've seen
                if (currentWindowSize < minLength) {
                    minLength = currentWindowSize;
                    minStart = left;
                }

                // The character at the 'left' pointer is about to be removed from the window
                char leftChar = session.charAt(left);
                windowCounts[leftChar]--;

                // If the character we are removing was strictly required by the target,
                // our window will become INVALID. We must decrease our matchedEvents counter.
                if (targetCounts[leftChar] > 0 && windowCounts[leftChar] < targetCounts[leftChar]) {
                    matchedEvents--;
                }

                // Actually shrink the window by moving the left pointer forward
                left++;
            }

            // Continue expanding the window
            right++;
        }

        // If minLength was never updated, we didn't find any valid window.
        return minLength == Integer.MAX_VALUE ? "" : session.substring(minStart, minStart + minLength);
    }
}
