package com.example.supple.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.supple.R;
import com.example.supple.activity.LoginActivity;
import com.example.supple.activity.ProductDetailsActivity;
import com.example.supple.activity.ProfileDetailActivity;
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

import static com.example.supple.activity.LoginActivity.BASE_URL;

public class ProfileFragment extends Fragment {
    private List<User> userList;
    private TextView tvFullName;
    private TextView tvPhoneNumber;
    private Button btnEditProfile;
    private LinearLayout llMyOrders;
    private LinearLayout llChangePass;
    private LinearLayout llShareFacebook;
    private LinearLayout llMyVoucher;
    private LinearLayout llSignOut;
    String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        getInformationUser();
        llChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = View.inflate(getContext(), R.layout.change_password_item, null);
                mBuilder.setView(mView);
                mBuilder.setCancelable(true);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                EditText edtPasswordChange;
                EditText edtRePasswordChange;
                Button btnSaveNewPassword;
                Button btnCancelForgetPass;
                edtPasswordChange = mView.findViewById(R.id.edtPasswordChange);
                edtRePasswordChange = mView.findViewById(R.id.edtRePasswordChange);
                btnSaveNewPassword = mView.findViewById(R.id.btnSaveNewPasswordChange);
                btnCancelForgetPass = mView.findViewById(R.id.btnCanceltPassChange);

                btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass = edtPasswordChange.getText().toString();
                        String repass = edtRePasswordChange.getText().toString();
                        String id = userList.get(0).getId();
                        String username = userList.get(0).getUsername();
                        if (pass.isEmpty()) {
                            edtPasswordChange.setError("Bạn không được để trống");
                            edtPasswordChange.requestFocus();
                        } else if (repass.isEmpty()) {
                            edtRePasswordChange.setError("Bạn không được để trống");
                            edtRePasswordChange.requestFocus();
                        } else if (!pass.matches(passPattern)) {
                            edtPasswordChange.setError("Mật khẩu ít nhất phải 8 ký tự gồm tối thiểu 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt");
                            edtPasswordChange.requestFocus();
                        } else if (!(pass.equals(repass))) {
                            edtRePasswordChange.setError("Mật khẩu không trùng khớp");
                            edtRePasswordChange.requestFocus();
                        } else {
                            changePass(id, username, pass);
                            dialog.dismiss();
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
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userList.get(0).getUsername();
                String password = userList.get(0).getPassword();
                String address = userList.get(0).getAddress();
                String email = userList.get(0).getEmail();
                String fullname = userList.get(0).getFullname();
                String phone = userList.get(0).getPhone();
                Intent intent = new Intent(getContext(), ProfileDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("password", password);
                bundle.putString("address", address);
                bundle.putString("email", email);
                bundle.putString("fullname", fullname);
                bundle.putString("phone", phone);
                intent.putExtra("dataUser", bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    public void initView(View view) {
        tvFullName = view.findViewById(R.id.tvFullName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        llMyOrders = view.findViewById(R.id.llMyOrders);
        llChangePass = view.findViewById(R.id.llChangePass);
        llShareFacebook = view.findViewById(R.id.llShareFacebook);
        llMyVoucher = view.findViewById(R.id.llMyVoucher);
        llSignOut = view.findViewById(R.id.llSignOut);
    }

    private void getInformationUser() {
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
                tvFullName.setText(userList.get(0).getFullname());
                tvPhoneNumber.setText(userList.get(0).getPhone());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("XXXXXX", t.getLocalizedMessage());
            }
        });
    }

    private void changePass(String id, String username, String password) {
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
        userService.changePass(id, username, password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Change Success")) {
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Change Fail")) {
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại. Xin kiểm tra lại", Toast.LENGTH_SHORT).show();
                } else if (response.body().equals("Duplicate")) {
                    Toast.makeText(getContext(), "Mật khẩu đã trung với mật khẩu cũ. Mời kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail Change Password", t.getLocalizedMessage());
            }
        });
    }
}
