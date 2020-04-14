package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adder {
    public static String add(String num1, String num2) {
        char[] num1Array = num1.toCharArray();
        char[] num2Array = num2.toCharArray();

        // first find the index of the decimal
        int num1DecimalAt = findDecimal(num1Array);
        int num2DecimalAt = findDecimal(num2Array);

        int num1DecimalPlaces = findDecimalPlaces(num1Array, num1DecimalAt);
        int num2DecimalPlaces = findDecimalPlaces(num2Array, num2DecimalAt);

        if (num1DecimalPlaces != num2DecimalPlaces) {
            if (num1DecimalPlaces > num2DecimalPlaces) {
                int howMany = num1DecimalPlaces - num2DecimalPlaces;

                if (num2DecimalAt == -1) {
                    num2Array = formatDecimalPlacesWithDecimal(num2Array, howMany);
                } else {
                    num2Array = formatDecimalPlaces(num2Array, howMany);
                }
            } else {
                int howMany = num2DecimalPlaces - num1DecimalPlaces;

                if (num1DecimalAt == -1) {
                    num1Array = formatDecimalPlacesWithDecimal(num1Array, howMany);
                } else {
                    num1Array = formatDecimalPlaces(num1Array, howMany);
                }
            }
        }

        if (num1Array.length != num2Array.length) {
            int howMany = Math.max(num1Array.length, num2Array.length) -
                    Math.min(num1Array.length, num2Array.length);

            if (num1Array.length > num2Array.length) {
                num2Array = formatIntegerPlaces(num2Array, howMany);
            } else {
                num1Array = formatIntegerPlaces(num1Array, howMany);
            }
        }

        char[] added = addArrays(num1Array, num2Array);
        return arrayToString(added);
    }

    private static String arrayToString(char[] nums) {
        return String.valueOf(nums);
    }

    private static char[] addArrays(char[] nums1, char[] nums2) {
        List<Character> added = new ArrayList<>();
        char[] nums1Reversed = reverseArray(nums1);
        char[] nums2Reversed = reverseArray(nums2);

        int carry = 0;

        for (int i = 0; i < nums1Reversed.length; i++) {

            if (nums1Reversed[i] == '.') {
                added.add('.');
                continue;
            }

            int num1 = Integer.parseInt(String.valueOf(nums1Reversed[i]));
            int num2 = Integer.parseInt(String.valueOf(nums2Reversed[i]));
            int sum = num1 + num2 + carry;

            added.add((char)((sum % 10)  + '0'));

            if (sum >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }
        }

        if (carry == 1) {
            added.add('1');
        }

        char[] result = new char[added.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = added.get(i);
        }
        return reverseArray(result);
    }

    private static char[] reverseArray(char[] nums) {
        char[] reversed = new char[nums.length];

        for (int i = 0; i < nums.length; i++) {
            reversed[i] = nums[nums.length - 1 - i];
        }

        return reversed;
    }

    private static char[] formatIntegerPlaces(char[] nums, int howMany) {
        char[] newArr = new char[nums.length + howMany];

        for (int i = 0; i < howMany; i++) {
            newArr[i] = '0';
        }

        for (int i = 0; i < nums.length; i++) {
            newArr[i + howMany] = nums[i];
        }

        return newArr;
    }

    // for debugging
    private static void printArrays(char[] nums1, char[] nums2) {
        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));
    }

    private static char[] formatDecimalPlaces(char[] nums, int howMany) {
        char[] newArr = Arrays.copyOf(nums, nums.length + howMany);

        for (int i = newArr.length - 1; i > nums.length - 1; i--) {
            newArr[i] = '0';
        }

        return newArr;
    }

    private static char[] formatDecimalPlacesWithDecimal(char[] nums, int howMany) {
        char[] newArr = Arrays.copyOf(nums, nums.length + howMany + 1);

        for (int i = newArr.length - 1; i > nums.length; i--) {
            newArr[i] = '0';
        }

        newArr[nums.length] = '.';

        return newArr;
    }

    private static int findDecimal(char[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == '.') {
                return i;
            }
        }

        return -1;
    }

    private static int findDecimalPlaces(char[] nums, int decimalAt) {
        if (decimalAt == -1) {
            return 0;
        }

        return nums.length - 1 - decimalAt;
    }
}
