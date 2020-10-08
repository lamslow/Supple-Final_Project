package com.example.supple.service;

import com.example.supple.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/myProduct")
    Call<List<Products>> getWheyProducts (@Query("Classify") String classify);
}
