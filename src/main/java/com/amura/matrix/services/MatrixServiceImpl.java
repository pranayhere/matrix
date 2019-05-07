package com.amura.matrix.services;

import com.amura.matrix.models.MaximumSubmatrix;

import org.springframework.stereotype.Service;

@Service

public class MatrixServiceImpl implements MatrixService {
    
    @Override
    public int maxSubmatrix(int[][] arr) {
        MaximumSubmatrix mrs = new MaximumSubmatrix();
        return mrs.maximum(arr);
    }
}