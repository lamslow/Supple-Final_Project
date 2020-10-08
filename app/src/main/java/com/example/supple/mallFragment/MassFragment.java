package com.example.supple.mallFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.supple.R;
import com.example.supple.adapter.ProductAdapter;
import com.example.supple.model.Products;
import com.example.supple.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.supple.activity.LoginActivity.BASE_URL;


public class MassFragment extends Fragment {
    public static final String MASS_PRODUCT="MASS";
    private List<Products> productsList;
    private ProductAdapter productAdapter;
    private Spinner spnSapXepMass;
    private RecyclerView rvMassDeals;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall_product, container, false);
        initView(view);
        productsList=new ArrayList<>();
        productAdapter=new ProductAdapter(getContext(),productsList);
        rvMassDeals.setAdapter(productAdapter);
        rvMassDeals.setLayoutManager(gridLayoutManager);
        getMassProduct(MASS_PRODUCT);
        return view;
    }

    private void initView(View view) {
        productsList=new ArrayList<>();
        spnSapXepMass = (Spinner) view.findViewById(R.id.spnSapXep);
        rvMassDeals = (RecyclerView) view.findViewById(R.id.rvDeals);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
    }

    private void getMassProduct(String classify){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        productService.getWheyProducts(classify).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                productsList.clear();
                List<Products> products =response.body();
                productsList.addAll(products);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage()+"");
            }
        });
    }
}
