package com.example.reviewcafe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment {
    private String data;
    private LoginFragment(){

    }
    public static LoginFragment newInstance(String data){
        Fragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",data);
        fragment.setArguments(bundle);
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (getArguments()!=null){
//            this.data=getArguments().getString("DATA");
//        }
        return inflater.inflate(R.layout.login,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.vpgLogin);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        FragmentManager fm = getFragmentManager();
        LoginAdapter adapter = new LoginAdapter(fm);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
