package com.example.reviewcafe.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.reviewcafe.fragment.SignInFragment;
import com.example.reviewcafe.fragment.SignUpFragment;

public class LoginAdapter extends FragmentStatePagerAdapter {
    public LoginAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        if (position==0){
            frag=SignInFragment.newInstance("login");
        }else{
            frag=new SignUpFragment();
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (position==0){
            title="Sign in";
        }else{
            title="Sign up";
        }
        return title;
    }
}
