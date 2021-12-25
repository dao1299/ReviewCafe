package com.example.reviewcafe.adapter;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reviewcafe.fragment.PhotoFragment;
import com.example.reviewcafe.model.PhotoModel;

import java.util.List;

public class PhotoAdapter extends FragmentStateAdapter{
    private List<PhotoModel> listPhoto;


    public PhotoAdapter(@NonNull Fragment fragment, List<PhotoModel> listPhoto) {
        super(fragment);
        this.listPhoto = listPhoto;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PhotoModel photo = listPhoto.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objPhoto",photo);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public int getItemCount() {
        return listPhoto.size();
    }
}
