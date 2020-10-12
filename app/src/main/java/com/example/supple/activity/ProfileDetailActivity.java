package com.example.supple.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supple.R;

public class ProfileDetailActivity extends AppCompatActivity {
    private TextView tvFullnane_User;
    private TextView tvUsernameUser;
    private TextView tvPasswordUser;
    private TextView tvPhoneUser;
    private TextView tvAddressUser;
    private TextView tvEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataUser");
        tvUsernameUser.setText(bundle.getString("username"));
        tvPasswordUser.setText(bundle.getString("password"));
        tvAddressUser.setText(bundle.getString("address"));
        tvEmailUser.setText(bundle.getString("email"));
        tvFullnane_User.setText( bundle.getString("fullname"));
        tvPhoneUser.setText(bundle.getString("phone"));
    }

    private void init() {
        tvFullnane_User = (TextView) findViewById(R.id.tvFullnane_User);
        tvUsernameUser = (TextView) findViewById(R.id.tvUsername_User);
        tvPasswordUser = (TextView) findViewById(R.id.tvPassword_User);
        tvPhoneUser = (TextView) findViewById(R.id.tvPhone_User);
        tvAddressUser = (TextView) findViewById(R.id.tvAddress_User);
        tvEmailUser = (TextView) findViewById(R.id.tvEmail_User);

    }
}