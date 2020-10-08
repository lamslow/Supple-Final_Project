package com.example.supple.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.supple.R;
import com.example.supple.dao.ProductInCartDAO;
import com.example.supple.model.ProductsInCart;
import com.example.supple.model.User;
import com.example.supple.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

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

public class ProductDetailsActivity extends AppCompatActivity {
    private List<User> userList;
    private TextView tvSpecies;
    private ImageView imgProductDetail;
    private TextView tvNameProductDetail;
    private TextView tvPriceProductDetail;
    private TextView tvDescriptionDetail;
    private Button btnAddProduct;
    private TextView tvSoldNumber;
    private TextView tvQuantity;
    private ProductInCartDAO productInCartDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initView();
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("data");
        String name=bundle.getString("nameSP");
        String classify=bundle.getString("type");
        String description=bundle.getString("motaSP");
        double price=bundle.getDouble("giaSP");
        String image=bundle.getString("imageSP");

        tvDescriptionDetail.setText(description);
        tvNameProductDetail.setText(name);
        tvPriceProductDetail.setText(String.valueOf(price));
        tvSpecies.setText(classify);
        Picasso.get().load(image).into(imgProductDetail);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsername();
                addToCart(name,price,image,userList.get(0).getUsername());
            }
        });
    }

    private void initView() {
        userList=new ArrayList<>();
        tvSpecies = (TextView) findViewById(R.id.tvSpecies);
        imgProductDetail = (ImageView) findViewById(R.id.imgProductDetail);
        tvNameProductDetail = (TextView) findViewById(R.id.tvNameProductDetail);
        tvPriceProductDetail = (TextView) findViewById(R.id.tvPriceProductDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        tvSoldNumber = (TextView) findViewById(R.id.tvSoldNumber);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        productInCartDAO = new ProductInCartDAO(this);
    }
    private void addToCart(String nameSP,double price,String image,String username){
        long result=productInCartDAO.insertProductCart(new ProductsInCart(null,image,price,nameSP,1,username));
        if (result>0){
            Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void getUsername(){
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
        UserService userService = retrofit.create(UserService.class);
        userService.getInforUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("FailGet", t.getLocalizedMessage());
            }
        });
    }
}