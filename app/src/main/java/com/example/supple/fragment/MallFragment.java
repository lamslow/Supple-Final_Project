package com.example.supple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.supple.R;
import com.example.supple.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MallFragment extends Fragment {

    private Button btnMallSearch;
    private ImageButton btnMallCart;
    private TextView tvMallNumberInCart;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnMallSearch = (Button) view.findViewById(R.id.btnMallSearch);
        btnMallCart = (ImageButton) view.findViewById(R.id.btnMallCart);
        tvMallNumberInCart = (TextView) view.findViewById(R.id.tvMallNumberInCart);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);



        FragmentManager manager = getActivity().getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }
}
