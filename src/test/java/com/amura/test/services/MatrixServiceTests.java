package com.amura.test.services;

import static org.junit.Assert.assertEquals;

import com.amura.matrix.services.MatrixServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MatrixServiceTests {

    private MatrixServiceImpl matrixService;
    
    @Before
    public void before() {
       this.matrixService = new MatrixServiceImpl();
    }

    @Test
    public void whenMatrixIsGiven_thenMaximumSubmatrixIsFound() {
        int[][] arr = {
            {1, 1, 1, 0},
            {1, 1, 1, 1},
            {0, 1, 1, 0},
            {0, 1, 1, 1},
            {1, 0, 0, 1},
            {1, 1, 1, 1}
        };
        assertEquals(8, matrixService.maxSubmatrix(arr));
    }
}