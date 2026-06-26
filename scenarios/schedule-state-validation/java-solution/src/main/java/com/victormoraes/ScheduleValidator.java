package com.victormoraes;

public class ScheduleValidator {

    public boolean canTransform(String start, String target) {
        // Time Complexity: Must be O(N)
        // Space Complexity: Must be O(1).

        // An L piece can only move to the left (replacing an adjacent _).
        // An R piece can only move to the right (replacing an adjacent _)
        // Pieces cannot cross each other.

        // Start evaluating current possibilities for the start scheduled slots
        // following the rules, where should the target position?

        int startIndex = 0;
        int targetIndex = 0;

        int length = start.length();

        while (startIndex < start.length() || targetIndex < target.length()) {

            // Jump the _ characters and find the next letter to compare it character value
            // and indexes
            while (startIndex < length && start.charAt(startIndex) == '_') {
                startIndex++;
            }

            while (targetIndex < length && target.charAt(targetIndex) == '_') {
                targetIndex++;
            }

            // Both reached the end
            if (startIndex == length && targetIndex == length)
                return true;

            // One String with more letters than the other
            if (startIndex == length || targetIndex == length)
                return false;

            // The same order | Can't jump out
            if (start.charAt(startIndex) == target.charAt(targetIndex))

                // L Rule || Can't move to right
                if (target.charAt(targetIndex) == 'L' && targetIndex > startIndex)
                    return false;

            // R rule || Can't move to left
            if (start.charAt(targetIndex) == 'R' && startIndex > targetIndex)
                return false;

        }

        return true;
    }
}
