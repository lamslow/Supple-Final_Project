package com.example.supple.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.supple.R;
import com.example.supple.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.supple.activity.LoginActivity.BASE_URL;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSUUsername;
    private EditText edtSUFullName;
    private EditText edtSUAddress;
    private EditText edtSUPhone;
    private EditText edtSUEmail;
    private EditText edtSUPassword;
    private EditText edtSURepassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtSUUsername.getText().toString();
                String fullname = edtSUFullName.getText().toString();
                String address = edtSUAddress.getText().toString();
                String phone = edtSUPhone.getText().toString();
                String email = edtSUEmail.getText().toString();
                String password = edtSUPassword.getText().toString();
                String repassword = edtSURepassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passPattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

                if (username.isEmpty()) {
                    edtSUUsername.setError("Tên đăng nhập không được để trống");
                } else if (fullname.isEmpty()) {
                    edtSUFullName.setError("Tên đày đủ không được để trống");
                } else if (address.isEmpty()) {
                    edtSUAddress.setError("Địa chỉ không được để trống");
                } else if (phone.isEmpty()) {
                    edtSUPhone.setError("Số điện thoại không được để trống");
                } else if (email.isEmpty()) {
                    edtSUEmail.setError("Email không được để trống");
                } else if (password.isEmpty()) {
                    edtSUPassword.setError("Mật khẩu không được để trống");
                } else if (repassword.isEmpty()) {
                    edtSURepassword.setError("Mật khẩu không được để trống");
                }else if(username.length()<6){
                    edtSUUsername.setError("Tên đăng nhập ít nhất có 6 kí tự");
                    edtSUUsername.requestFocus();
                } else if (!(phone.length() == 10)) {
                    edtSUPhone.setError("Số điện thoại phải đủ 10 ký tự");
                } else if (!email.matches(emailPattern)) {
                    edtSUEmail.requestFocus();
                    edtSUEmail.setError("Email không đúng định dạng");
                } else if (!password.matches(passPattern)) {
                    edtSUPassword.requestFocus();
                    edtSUPassword.setError("Mật khẩu ít nhất phải 8 ký tự gồm tối thiểu 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt");
                } else if (!password.equalsIgnoreCase(repassword)) {
                    edtSUPassword.setError("Mật khẩu không trùng khớp");
                    edtSUPassword.requestFocus();
                    edtSUPassword.setText(null);
                    edtSURepassword.setText(null);
                } else {
                    signUp(username,password,phone,address,email,fullname);
                }

            }
        });


    }

    private void signUp(String username,String password,String phone,String address,String email,String fullname) {
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
        userService.signUp(username,password,fullname,phone,email,address).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Create Account Success")){
                    Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công. Mời bạn đăng nhập", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else if (response.body().equals("Create Account Failure")){
                    Toast.makeText(SignUpActivity.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }else if (response.body().equals("Account already exists")){
                    Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }else if (response.body().equals("Email hoặc Số điện thoại đã được sử dụng")){
                    Toast.makeText(SignUpActivity.this, response.body()+"", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("QQQ",t.getLocalizedMessage()+"");
            }
        });

    }

    private void init() {
        edtSUUsername = (EditText) findViewById(R.id.edtSU_Username);
        edtSUFullName = (EditText) findViewById(R.id.edtSU_FullName);
        edtSUAddress = (EditText) findViewById(R.id.edtSU_Address);
        edtSUPhone = (EditText) findViewById(R.id.edtSU_Phone);
        edtSUEmail = (EditText) findViewById(R.id.edtSU_Email);
        edtSUPassword = (EditText) findViewById(R.id.edtSU_Password);
        edtSURepassword = (EditText) findViewById(R.id.edtSU_Repassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

    }
}