package com.amura.matrix.models;

public class MaximumSubmatrix {
    public int maximum(int input[][]) {
        int temp[] = new int[input[0].length];
        MaximumHistogram mh = new MaximumHistogram();
        int maxArea = 0;
        int area = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (input[i][j] == 0) {
                    temp[j] = 0;
                } else {
                    temp[j] += input[i][j];
                }
            }
            area = mh.maxHistogram(temp);
            if (area > maxArea) {
                maxArea = area;
            }
        }
        return maxArea;
    }
}