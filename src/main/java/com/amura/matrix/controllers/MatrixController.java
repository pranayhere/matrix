package com.amura.matrix.controllers;

import java.util.HashMap;
import java.util.Map;

import com.amura.matrix.services.MatrixService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

    @Autowired
    MatrixService matrixService;
    
    // http://localhost:8080/matrix/max-submatrix?m=6&n=4&arr=1,1,1,0,1,1,1,1,0,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1
    @GetMapping("/matrix/max-submatrix")
    public Map<String, String> maxSubarray(@RequestParam("m") int m, @RequestParam("n") int n, @RequestParam("arr") String matrixStr) {
        Map<String, String> hm = new HashMap<String, String>();
        String[] matrix = matrixStr.split(",");
		int[][] arr = new int[m][n];

        for(int i=0; i<m; i++) 
            for(int j=0; j<n; j++) 
                arr[i][j] = Integer.valueOf(matrix[(i*n) + j]);

        hm.put("msg", "Maxium Submatrix is : " + matrixService.maxSubmatrix(arr));
        return hm;
    }
}