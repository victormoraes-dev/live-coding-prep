package com.victormoraes;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // array nums and a target number
    // return indices of two number add up to target number

    public int[] twoSum(int[] nums, int target) {

        // array: 2, 7, 11, 15
        // target = 9

        // target = x + y -> complement = target - y, where y is the current value

        // HashMap -> map processedValues | array value as key and array index as value
        // get the indexes from hashmap

        // Time complexity O(n)
        // Space complexity O(n)
        Map<Integer, Integer> visited = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int complement = target - nums[i];

            if(visited.containsKey(complement)){
                return new int[]{visited.get(complement), i};
            }

            visited.put(nums[i], i);
        }

        return new int[]{};
    }

    // Space Complexity O(1)
    public int[] twoSumV2(int[] nums, int target){

        
    }
}
