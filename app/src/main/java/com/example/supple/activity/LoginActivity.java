package com.example.supple.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.supple.R;
import com.example.supple.model.User;
import com.example.supple.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://192.168.102.27:1212";
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnSignIn;
    private TextView tvForgotPass;
    private TextView tvDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        boolean status = isNetworkOnline();
        if (!status) {
            Toast.makeText(this, "Vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
        } else {
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    if (username.isEmpty()) {
                        edtUsername.setError("Tên đăng nhập không được để trống");
                    } else if (password.isEmpty()) {
                        edtPassword.setError("Mật khẩu không được để trống");
                    } else {
                        signIn(username, password);
                    }


                }
            });
            tvDangKy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });

            tvForgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                    View mView = View.inflate(LoginActivity.this, R.layout.forget_password_item, null);
                    mBuilder.setView(mView);
                    mBuilder.setCancelable(true);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    EditText edtYourUsername;
                    EditText edtYourPhone;
                    EditText edtYourEmail;
                    Button btnSaveNewPassword;
                    Button btnCancelForgetPass;
                    EditText edtYourPassword;
                    EditText edtYourRePassword;

                    edtYourUsername = mView.findViewById(R.id.edtYourUsername);
                    edtYourPhone = mView.findViewById(R.id.edtYourPhone);
                    edtYourEmail = mView.findViewById(R.id.edtYourEmail);
                    edtYourPassword = mView.findViewById(R.id.edtYourPassword);
                    edtYourRePassword = mView.findViewById(R.id.edtYourRePassword);
                    btnSaveNewPassword = mView.findViewById(R.id.btnSaveNewPassword);
                    btnCancelForgetPass = mView.findViewById(R.id.btnCancelForgetPass);


                    btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String username = edtYourUsername.getText().toString();
                            String phone = edtYourPhone.getText().toString();
                            String email = edtYourEmail.getText().toString();
                            String pass = edtYourPassword.getText().toString();
                            String rePas = edtYourRePassword.getText().toString();
                            if (username.isEmpty()) {
                                edtYourUsername.setError("Không được để trống");
                                edtYourUsername.requestFocus();
                            } else if (phone.isEmpty()) {
                                edtYourPhone.setError("Không được để trống");
                                edtYourPhone.requestFocus();
                            } else if (email.isEmpty()) {
                                edtYourEmail.setError("Không được để trống");
                                edtYourEmail.requestFocus();
                            } else if (pass.isEmpty()) {
                                edtYourPassword.setError("Không được để trống");
                                edtYourPassword.requestFocus();
                            } else if (rePas.isEmpty()) {
                                edtYourRePassword.setError("Không được để trống");
                                edtYourRePassword.requestFocus();
                            } else if (!(pass.equals(rePas))) {
                                edtYourUsername.setError("Mật khẩu không trùng khớp");
                                edtYourPassword.requestFocus();
                            } else {
                                getUser(username, phone, email, pass);
                            }

                        }
                    });

                    btnCancelForgetPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
            });
        }
    }

    private void getUser(String username, String phone, String email, String pass) {
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
        userService.getAllUsers(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                String id = response.body().get(0).getId();
                resetPass(id, username, pass, phone, email);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void resetPass(String id, String username, String pass, String phone, String email) {
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
        userService.resetPass(id, username, pass, phone, email).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Reset Success")) {
                    Toast.makeText(LoginActivity.this, "Reset Success", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Reset Fail")) {
                    Toast.makeText(LoginActivity.this, "Reset Fail", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Incorrectly")) {
                    Toast.makeText(LoginActivity.this, "Kiểm tra lại Username, Phone, Email", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }


    private void signIn(String username, String password) {
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

        userService.login(username, password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Admin")) {
                    Toast.makeText(LoginActivity.this, "Bạn là Admin", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Success")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Fail")) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất  bại. Vui lòng kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Sever hiện không hoạt động. Vui lòng đợi!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean isNetworkOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getNetworkInfo(0);
        return netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    public void initView() {
        tvForgotPass = findViewById(R.id.tvForgotPass);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvDangKy = findViewById(R.id.tvDangKy);


    }
}