package com.example.supple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supple.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Button btnSearch;
    private ImageButton btnCart;
    private TextView tvNumberInCart;
    private RecyclerView rvSuppleProduct;
    private GridLayoutManager gridLayoutManager;
    private LinearLayout lnWhey;
    private LinearLayout lnMass;
    private LinearLayout lnBCAA;
    private LinearLayout liner;
    private ViewFlipper viewFlipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lnWhey = (LinearLayout) view.findViewById(R.id.lnWhey);
        lnMass = (LinearLayout) view.findViewById(R.id.lnMass);
        lnBCAA = (LinearLayout) view.findViewById(R.id.lnBCAA);
        liner = (LinearLayout) view.findViewById(R.id.liner);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnCart = (ImageButton) view.findViewById(R.id.btnCart);
        tvNumberInCart = (TextView) view.findViewById(R.id.tvNumberInCart);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vpSlider);
        rvSuppleProduct = (RecyclerView) view.findViewById(R.id.rvSuppleProduct);
    }
}
