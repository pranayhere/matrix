package com.amura.test.services;

import static org.junit.Assert.assertEquals;

import com.amura.matrix.services.MatrixServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MatrixServiceTests {

    MatrixServiceImpl matrixService  = new MatrixServiceImpl();

    @Test
    public void brackets() {
        int[][] arr = {
            {1, 1, 1, 0},
            {1, 1, 1, 1},
            {0, 1, 1, 0},
            {0, 1, 1, 1},
            {1, 0, 0, 1},
            {1, 1, 1, 1}
        };
        final int ans = matrixService.maxSubmatrix(arr);
        assertEquals(8, ans);
    }
}